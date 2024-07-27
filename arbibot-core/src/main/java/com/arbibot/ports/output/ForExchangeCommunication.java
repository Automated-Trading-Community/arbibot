package com.arbibot.ports.output;

import com.arbibot.entities.Order;
import com.arbibot.entities.Pair;

import java.math.BigDecimal;
import java.util.List;

import com.arbibot.entities.Asset;
import com.arbibot.entities.Exchange;

public interface ForExchangeCommunication {

    /**
     * 
     * Recover the price for a Pair.
     * 
     * @return {@code BigDecimal}
     */
    void getPriceForPair(Pair pair, Exchange exchange);

    /**
     * Pass orders and update their status.
     * 
     * @see com.arbibot.entities.Order
     */
    void passOrders(Order[] orders);

    /**
     * Recover the balance for a specific asset.
     * 
     * @param asset    The asset to get the balance for.
     * @param exchange The exchange to get the balance from.
     * @return {@code BigDecimal} The balance of the asset.
     */
    BigDecimal getBalanceForAsset(Asset asset, Exchange exchange) throws Exception;

    /**
     * Retrieve information about a specific order.
     * 
     * @param orderId  The ID of the order to get information about.
     * @param exchange The exchange to get the order information from.
     * @return {@code Order} The order information.
     */
    Order getOrderInfo(String orderId, Exchange exchange);

    /**
     * Recover the list of available trading pairs on the exchange.
     * 
     * @param exchange The exchange to get the trading pairs from.
     * @return {@code List<Pair>} A list of available trading pairs.
     */
    List<Pair> getAvailableTradingPairs(Exchange exchange);
}