package com.arbibot.infra.adapter.forexchangecommunication.binance;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.arbibot.core.entities.Asset;
import com.arbibot.core.entities.Exchange;
import com.arbibot.core.entities.Order;
import com.arbibot.core.entities.Pair;
import com.arbibot.core.ports.output.ForExchangeCommunication;
import com.arbibot.infra.adapter.forexchangecommunication.binance.exceptions.BinanceDeserializerException;
import com.arbibot.infra.adapter.forexchangecommunication.binance.exceptions.BinancePermissionApiKeysException;
import com.arbibot.infra.adapter.forexchangecommunication.binance.exceptions.BinanceWrongApiKeys;
import com.arbibot.infra.adapter.forexchangecommunication.binance.factories.BinanceOrderFactory;
import com.arbibot.infra.adapter.forexchangecommunication.binance.models.ApiPermissionsBinance;
import com.arbibot.infra.adapter.forexchangecommunication.binance.models.AssetBinance;
import com.arbibot.infra.adapter.forexchangecommunication.binance.models.MiniTicker;

import com.binance.connector.client.SpotClient;
import com.binance.connector.client.WebSocketStreamClient;
import com.binance.connector.client.enums.DefaultUrls;
import com.binance.connector.client.exceptions.BinanceClientException;
import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.connector.client.impl.WebSocketStreamClientImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;

/**
 * Implementation of the ForExchangeCommunication interface for Binance
 * Exchange.
 * 
 * @author SChoumiloff
 * @author SebastienGuillemin
 * @since 1.0
 */
@Service
public class Binance implements ForExchangeCommunication {

    private Map<String, BigDecimal> prices = new HashMap<>();
    private Map<String, Integer> sockets = new HashMap<>();
    private Map<String, CompletableFuture<BigDecimal>> priceFutures = new HashMap<>();
    private SpotClient clientSpot;
    private WebSocketStreamClient wsClient;

    @Value("${binance.api.key:#{null}}")
    private String apiKEY;
    @Value("${binance.api.secret:#{null}}")
    private String apiSECRET;

    /**
     * Initializes the Binance exchange with the given API key and secret.
     * it also call manageWebSocketForUserEvent to listen to user events
     * 
     * @param apiKey
     * @param apiSecret
     * @throws BinanceDeserializerException
     * @throws BinanceWrongApiKeys
     */
    public Binance() {
    }

    @PostConstruct
    public void init() {
        if (this.apiKEY == null || this.apiSECRET == null) {
            return;
        }
        this.clientSpot = new SpotClientImpl(this.apiKEY, this.apiSECRET);
        try {
            this.verifyPermissions();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // TODO : pourquoi ne pas utiliser @Autowired ?
        // INFO : comment tu veux faire ?
        this.wsClient = new WebSocketStreamClientImpl(DefaultUrls.WS_URL);
    }

    /**
     * Retrieves the price for a given pair from the Binance exchange.
     *
     * @param pair     the pair for which to retrieve the price
     * @param exchange the Binance exchange instance
     * 
     */
    @Override
    public void getPriceForPair(Pair pair) {
        try {
            String symbol = pair.getSymbol();
            if (this.prices.containsKey(symbol)) {
                pair.setPrice(this.prices.get(symbol));
            } else {
                CompletableFuture<BigDecimal> future = new CompletableFuture<>();
                this.priceFutures.put(symbol, future);
                this.openWebSocketMiniTicker(pair);
                BigDecimal price = future.get();
                pair.setPrice(price);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void passOrder(Order order) {
        Map<String, Object> parameters = BinanceOrderFactory.createOrderFromOrderObj(order);
        try {
            String result = this.clientSpot.createTrade().newOrder(parameters);

        } catch (Exception e) {
            e.printStackTrace();
        }
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
        // INFO seb j'ai gérer d'une manière différente pour ne pas stoppé l'execution
        // avec un Thread.sleep()
        // j'ai utiliser les CompletableFuture. c'est l'équivalent d'un wait() et
        // notify() mais de manière plus simple.
        // tant que symbol est a null alors on attend. La future est complété quand on
        // recoit le message
        int socketId = this.wsClient.miniTickerStream(pair.getSymbol(),
                (messageOpenEvent) -> {
                    this.prices.put(pair.getSymbol(), null);
                },
                (messageEvent) -> {
                    MiniTicker ticker;
                    try {
                        ticker = deserializeSymbolTickerBinance(messageEvent);
                        BigDecimal price = ticker.getClosePrice();
                        this.prices.put(pair.getSymbol(), price);
                        CompletableFuture<BigDecimal> future = this.priceFutures.remove(pair.getSymbol());
                        if (future != null) {
                            future.complete(price);
                        }
                    } catch (BinanceDeserializerException e) {
                        e.printStackTrace();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                },
                null,
                (code, reason) -> {
                    this.sockets.remove(pair.getSymbol());
                    this.prices.remove(pair.getSymbol());
                    this.wsClient.closeConnection(this.sockets.get(pair.getSymbol()));
                },
                null);
        this.sockets.put(pair.getSymbol(), socketId);
    }

    /**
     * Deserializes the given data into a MiniTicker object
     * 
     * @param data
     * @return {@code MiniTicker}
     * @throws BinanceDeserializerException
     */
    private MiniTicker deserializeSymbolTickerBinance(String data) throws BinanceDeserializerException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            MiniTicker ticker = objectMapper.readValue(data, MiniTicker.class);
            return ticker;
        } catch (Exception e) {
            throw new BinanceDeserializerException("Error during deserialization on " + data);
        }
    }

    /**
     * Verifies the permissions of the Binance API keys by making a request to the
     * Binance API and checking the response.
     * If the response indicates that the API keys are wrong or missing, a
     * `BinanceWrongApiKeys` exception is thrown.
     * If there is an error during the deserialization of the response, a
     * `BinanceDeserializerException` is thrown.
     *
     * @throws BinanceWrongApiKeys               if the API keys are wrong or
     *                                           missing
     * @throws BinanceDeserializerException      if there is an error during
     *                                           deserialization
     * @throws BinancePermissionApiKeysException if the API keys have insufficient
     *                                           permissions
     * 
     */
    private void verifyPermissions()
            throws BinanceWrongApiKeys, BinanceDeserializerException, BinancePermissionApiKeysException {
        try {
            String permissions = this.clientSpot.createWallet().apiPermission(null);
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                ApiPermissionsBinance apiPermissions = objectMapper.readValue(permissions, ApiPermissionsBinance.class);
                if (!isPermissionsOk(apiPermissions)) {
                    throw new BinancePermissionApiKeysException("Wrong permissions");
                }
            } catch (JsonProcessingException jpe) {
                throw new BinanceDeserializerException("error during deserialization on " + permissions);
            }
        } catch (BinanceClientException e) {
            if (e.getErrorCode() == -1022) {
                throw new BinanceWrongApiKeys("Wrong or missing Binance API keys.");
            }
        }
    }

    /**
     * Checks if the given API permissions are valid.
     *
     * @param permissions the API permissions to check
     * @return true if the permissions allow reading and spot and margin trading,
     *         false otherwise
     */
    private boolean isPermissionsOk(ApiPermissionsBinance permissions) {
        return permissions.isEnableReading() && permissions.isEnableSpotAndMarginTrading();
    }
}
