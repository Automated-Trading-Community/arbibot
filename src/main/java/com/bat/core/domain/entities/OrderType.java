package com.bat.core.domain.entities;

/**
 * The {@code OrderType} enum represents the different types of orders that can be placed in the trading system.
 * Each type of order has its own specific behavior and use case in trading.
 * 
 * Types of orders:
 * <ul>
 * <li>{@link #LIMIT}: An order to buy or sell an asset at a specified price or better.</li>
 * <li>{@link #MARKET}: An order to buy or sell an asset immediately at the best available current price.</li>
 * <li>{@link #OCO}: "One Cancels the Other" order, where placing one order cancels the other.</li>
 * <li>{@link #TRAILING_STOP}: An order that sets the stop price at a fixed amount below the market price with an attached "trailing" amount.</li>
 * </ul>
 * 
 * 
 * @author SChoumiloff
 * @since 1.0
 */
public enum OrderType {
    /**
     * A limit order is an order to buy or sell an asset at a specified price or better.
     * Limit orders ensure that the trader gets the desired price or better, but they do not guarantee execution.
     */
    LIMIT,

    /**
     * A market order is an order to buy or sell an asset immediately at the best available current price.
     * Market orders ensure execution but do not guarantee the price.
     */
    MARKET,

    /**
     * An OCO (One Cancels the Other) order is a pair of orders placed together.
     * If one of the orders is executed, the other is automatically canceled.
     */
    OCO,

    /**
     * A trailing stop order is a stop order that can be set at a defined percentage or dollar amount away from an asset's current market price.
     * The stop price is adjusted as the price fluctuates.
     */
    TRAILING_STOP,

    /**
     * A stop limit order is a limit order that can be set at a defined percentage or dollar amount away from an asset's current market price.
     * The stop price is adjusted as the price fluctuates.
     */
    STOP_LIMIT
}