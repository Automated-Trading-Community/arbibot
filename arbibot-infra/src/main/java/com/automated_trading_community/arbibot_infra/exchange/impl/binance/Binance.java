package com.automated_trading_community.arbibot_infra.exchange.impl.binance;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;

import com.arbibot.entities.Asset;
import com.arbibot.entities.Exchange;
import com.arbibot.entities.Order;
import com.arbibot.entities.Pair;
import com.arbibot.ports.output.ForExchangeCommunication;
import com.automated_trading_community.arbibot_infra.exchange.impl.binance.exceptions.BinanceDeserializerException;
import com.automated_trading_community.arbibot_infra.exchange.impl.binance.factories.BinanceEventFactory;
import com.automated_trading_community.arbibot_infra.exchange.impl.binance.models.AssetBinance;
import com.automated_trading_community.arbibot_infra.exchange.impl.binance.models.MiniTicker;
import com.automated_trading_community.arbibot_infra.exchange.impl.binance.models.UserDataEventBinance;
import com.binance.connector.client.SpotClient;
import com.binance.connector.client.WebSocketStreamClient;
import com.binance.connector.client.enums.DefaultUrls;
import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.connector.client.impl.WebSocketStreamClientImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Binance implements ForExchangeCommunication {

    private Map<String, BigDecimal> prices = new HashMap<>();
    private Map<String, Integer> sockets = new HashMap<>();
    private SpotClient clientSpot;
    private WebSocketStreamClient wsClient;
    private Integer socketUserStreamId = null;

    private BufferEvent<UserDataEventBinance> userEvents; // TODO define type for userEventList

    /**
     * Initializes the Binance exchange with the given API key and secret.
     * it also call manageWebSocketForUserEvent to listen to user events
     * 
     * @param apiKey
     * @param apiSecret
     */
    public Binance(@Value("${binance.api.key}") String apiKey,
            @Value("${binance.api.secret}") String apiSecret) {
        this.clientSpot = new SpotClientImpl(apiKey, apiSecret);
        this.wsClient = new WebSocketStreamClientImpl(DefaultUrls.WS_URL);
        this.manageWebSocketForUserEvent();
    }

    /**
     * Retrieves the price for a given pair from the Binance exchange.
     *
     * @param pair     the pair for which to retrieve the price
     * @param exchange the Binance exchange instance
     * @throws BinanceDeserializerException if there is an error deserializing the
     *                                      response from Binance
     */
    @Override
    public void getPriceForPair(Pair pair, Exchange exchange) {
        try {
            String symbol = pair.getBaseAsset() + "" + pair.getQuoteAsset();
            if (this.prices.containsKey(symbol)) {
                pair.setPrice(this.prices.get(symbol));
            } else {
                this.openWebSocketMiniTicker(pair);
                while (this.prices.get(symbol) == null) {
                    Thread.sleep(100);
                }
                pair.setPrice(this.prices.get(symbol));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void passOrders(Order[] orders) {
        // TODO utiliser une websocket userEvents pour recevoir les infos sur les ordres
        // executer en temps réel et passer les autres
        // pour cela il faut générer une listen key avec la fonction private défini dans
        // cette classe et ensuite se brancher sur la
        // websocket listenUserStream. La websocket est a renouveller toutes les heures
        // (60 minutes)
        // use userEventList
        throw new UnsupportedOperationException("Unimplemented method 'passOrders'");
    }

    /**
     * Retrieves the balance for a specific asset from the Binance exchange.
     *
     * @param asset    the asset for which to retrieve the balance
     * @param exchange the Binance exchange instance
     * @return the balance of the asset as a BigDecimal, or null if the asset is not
     *         found
     * @throws BinanceDeserializerException if there is an error deserializing the
     *                                      response from Binance
     */
    @Override
    public BigDecimal getBalanceForAsset(Asset asset, Exchange exchange) throws BinanceDeserializerException {
        Map<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("omitZeroBalances", true);
        String result = this.clientSpot.createTrade().account(parameters);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode = objectMapper.readTree(result);
            JsonNode balancesNode = rootNode.path("balances");
            List<AssetBinance> balanceList = new ArrayList<>();
            Iterator<JsonNode> elements = balancesNode.elements();

            while (elements.hasNext()) {
                JsonNode balanceNode = elements.next();
                AssetBinance balance = objectMapper.treeToValue(balanceNode,
                        AssetBinance.class);
                balanceList.add(balance);
                if (asset.getName().equals(balance.getAsset()))
                    return new BigDecimal(balance.getFree());
            }
        } catch (Exception e) {
            throw new BinanceDeserializerException(e.getMessage());
        }
        return null;
    }

    @Override
    public Order getOrderInfo(String orderId, Exchange exchange) {
        throw new UnsupportedOperationException("Unimplemented method 'getOrderInfo'");
    }

    @Override
    public List<Pair> getAvailableTradingPairs(Exchange exchange) {
        throw new UnsupportedOperationException("Unimplemented method 'getAvailableTradingPairs'");
    }

    /**
     * Opens a WebSocket stream for the given pair
     * On opening the stream, the price is set to null
     * On receiving a message, the price is stored in the prices map
     * On close the socket is removed from the sockets map
     * 
     * @param pair
     */
    private void openWebSocketMiniTicker(Pair pair) {
        String symbol = pair.getBaseAsset() + "" + pair.getQuoteAsset();
        int socketId = this.wsClient.miniTickerStream(symbol,
                (messageOpenEvent) -> {
                    this.prices.put(symbol, null);
                },
                (messageEvent) -> {
                    MiniTicker ticker;
                    try {
                        ticker = serializeSymbolTickerBinance(messageEvent);
                        this.prices.replace(symbol, ticker.getClosePrice());
                    } catch (BinanceDeserializerException e) {
                        e.printStackTrace();
                    }
                },
                null,
                (code, reason) -> {
                    this.sockets.remove(symbol);
                    this.prices.remove(symbol);
                },
                null);
        // TODO define onFailureCallback
        this.sockets.put(symbol, socketId);
    }

    /**
     * Manages the WebSocket connection for user events
     * listenKey is updated each 60 minutes
     * all events are save in a {@code BufferEvent<UserDataEventBinance>}
     */
    private void manageWebSocketForUserEvent() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        Runnable task = () -> {
            try {
                if (this.socketUserStreamId != null) {
                    this.wsClient.closeConnection(this.socketUserStreamId);
                }
                String key = this.generateListenKey();
                this.socketUserStreamId = this.wsClient.listenUserStream(
                        key,
                        null,
                        (event) -> {
                            try {
                                this.userEvents.add(BinanceEventFactory.createEvent(event));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }, null, null, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        long initialDelay = 0;
        long period = 60;
        scheduler.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.MINUTES);
    }

    /**
     * Deserializes the given data into a MiniTicker object
     * 
     * @param data
     * @return {@code MiniTicker}
     * @throws BinanceDeserializerException
     */
    private MiniTicker serializeSymbolTickerBinance(String data) throws BinanceDeserializerException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            MiniTicker ticker = objectMapper.readValue(data, MiniTicker.class);
            return ticker;
        } catch (Exception e) {
            throw new BinanceDeserializerException("Error during deserialization on " + data);
        }
    }

    /**
     * 
     * Generate a listenkey to be used in the websocket {@code listenUserStream}
     * 
     * @return {@code String}
     */
    private String generateListenKey() {
        JSONObject obj = new JSONObject(clientSpot.createUserData().createListenKey());
        return obj.getString("listenKey");
    }
}
