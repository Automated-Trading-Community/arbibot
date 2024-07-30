package com.arbibot.ports.output;

import com.arbibot.entities.Order;
import com.arbibot.entities.Pair;

import java.math.BigDecimal;
import java.util.List;

import com.arbibot.entities.Asset;
import com.arbibot.entities.Exchange;

/**
 * Output port used to retrieve information from an exchange.
 * 
 * @author SChoumiloff
 * @author SebastienGuillemin
 * @since 1.0
 */
public interface ForExchangeCommunication {

    /**
     * Retrieve the price for a {@code pair} on a specific {@code exchange}.
     * 
     * @param pair     the pair whose price needs to be retrieve.
     * @param exchange the exchange on which the price is retrieved.
     * 
     * @return the price of a pair on a given exchange.
     */
    BigDecimal getPriceForPair(Pair pair, Exchange exchange);

    /**
     * Pass orders and update their status.
     * 
     * @param orders the orders to pass.
     * 
     */
    void passOrders(Order[] orders);

    /**
     * Retrieve the balance for a specific {@code asset} on a specific
     * {@code exchange}.
     * 
     * @param asset    The asset to get the balance for.
     * @param exchange The exchange to get the balance from.
     * 
     * @return {@link BigDecimal} The balance of the asset.
     */
    BigDecimal getBalanceForAsset(Asset asset, Exchange exchange) throws Exception;

    /**
     * Retrieve information about the order whose ID is {@code orderId} on a
     * {@code exchange}.
     * 
     * 
     * @param orderId  The ID of the order to get information about.
     * @param exchange The exchange to get the order information from.
     * 
     * @return An {@link Order} corresponding to the {@code orderId}.
     */
    Order getOrderInfo(String orderId, Exchange exchange);

    /**
     * Retrieve the list of available trading pairs on an exchange.
     * 
     * @param exchange The exchange to get the trading pairs from.
     * 
     * @return A {@link List} of available {@link Pair}s.
     */
    List<Pair> getAvailableTradingPairs(Exchange exchange);
}