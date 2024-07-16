package com.bat.domain;

/**
 * Enumeration representing the status of an order.
 * <p>
 * This enum is used to define the various states an order can be in
 * within the system. The possible states are:
 * </p>
 * <ul>
 * <li>{@link #EXECUTED} - The order has been completed successfully.</li>
 * <li>{@link #CANCELED} - The order has been canceled and will not be
 * processed.</li>
 * <li>{@link #OPEN} - The order is open and pending further action.</li>
 * </ul>
 * 
 * <p>
 * Usage example:
 * </p>
 * 
 * <pre>
 * {@code
 * OrderStatus status = OrderStatus.EXECUTED;
 * if (status == OrderStatus.EXECUTED) {
 *     // Handle executed order
 * }
 * }
 * </pre>
 * 
 * @author SChoumiloff
 * @since 1.0
 */
public enum OrderStatus {

    /**
     * Indicates that the order has been completed successfully.
     */
    EXECUTED,

    /**
     * Indicates that the order has been canceled and will not be processed.
     */
    CANCELED,

    /**
     * Indicates that the order is open and pending further action.
     */
    OPEN
}