package com.bat.domain;

/**
 * The {@code OrderAction} enum represents the possible actions for an order in
 * the trading system.
 * An order can be either a buy or a sell action.
 *
 * Types of actions:
 * <ul>
 * <li>{@link #BUY}: Indicates that the order is to purchase the trading pair's
 * base asset.</li>
 * <li>{@link #SELL}: Indicates that the order is to sell the trading pair's
 * base asset.</li>
 * </ul>
 *
 * @author SChoumiloff
 * @since 1.0
 */
public enum OrderAction {
    /**
     * Represents a buy action, indicating that the order is to purchase the trading
     * pair's base asset.
     */
    BUY,

    /**
     * Represents a sell action, indicating that the order is to sell the trading
     * pair's base asset.
     */
    SELL
}