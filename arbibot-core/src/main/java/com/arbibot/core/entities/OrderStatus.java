package com.arbibot.core.entities;

/**
 * Enumerates the different possible status for an order.
 * 
 * @author SChoumiloff
 * @author SebastienGuillemin
 * @since 1.0
 * 
 */
public enum OrderStatus {
    /**
     * The order has been rejected by the exchange.
     */
    REJECTED,

    /**
     * The order has been fully executed.
     */
    EXECUTED,

    /**
     * The order has not been executed or rejected yet.
     */
    PENDING,

    /**
     * The order has been created locally but has not yet been sent to the exchange.
     */
    NOT_PASSED
}