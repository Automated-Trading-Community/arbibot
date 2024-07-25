package com.arbibot.entities;

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
     * The order has been rejected by the exchangeµ.
     */
    REJECTED,

    /**
     * The order has been fully executed.
     */
    EXECUTED,

    /**
     * The order has not been executed or rejected yet.
     */
    PENDING
}
