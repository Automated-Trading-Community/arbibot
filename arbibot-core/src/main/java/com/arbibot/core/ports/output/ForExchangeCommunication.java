package com.arbibot.core.ports.output;

import java.math.BigDecimal;
import java.util.List;

import com.arbibot.core.entities.Asset;
import com.arbibot.core.entities.Exchange;
import com.arbibot.core.entities.Order;
import com.arbibot.core.entities.Pair;
import com.arbibot.core.exceptions.entities.ports.output.ExchangePairPriceException;

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
     * @param pair the pair whose price needs to be retrieve.
     * 
     * @return the price of a pair on a given exchange.
     * 
     * @throws ExchangePairPriceException if an error occurs when retrieving the
     *                                    price for the {@cpde pair}.
     */
    void getPriceForPair(Pair pair) throws ExchangePairPriceException;

    // /**
    // * Pass orders and update their status.
    // *
    // * @param orders the orders to pass.
    // *
    // */
    // void passOrders(Order[] orders);

    /**
     * 
     * Pass an order and update its status.
     * 
     * @param order
     */
    void passOrder(Order order);

    /**
     * Retrieve the balance for a specific {@code asset} on a specific
     * {@code exchange}.
     * 
     * @param asset    The asset to get the balance for.
     * @param exchange The exchange to get the balance from.
     * 
     * @return {@link BigDecimal} The balance of the asset.
     * 
     * @throws Exception if something goes wrong.
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