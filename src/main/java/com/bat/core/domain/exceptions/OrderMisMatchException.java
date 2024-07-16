package com.bat.core.domain.exceptions;

/**
 * The {@code OrderValidationException} class represents an exception that is
 * thrown
 * when an filled qtt of an order does not fit with the base qtt.
 * <p>
 * This exception is used to indicate specific validation errors that occur
 * during the order validation process.
 * It extends the {@code Exception} class and provides a constructor to create
 * an instance with a custom error message.
 * </p>
 * <p>
 * Example usage:
 * 
 * <pre>
 * {@code
 * try {
 *     order.getTotalFilledQuantity()
 * } catch (OrderMisMatchException e) {
 *     System.out.println("Validation error: " + e.getMessage());
 *     // Handle the validation error, such as logging or notifying the user
 * }
 * }
 * </pre>
 *
 * @see com.bat.core.domain.entities.Order#getTotalFilledQuantity()
 * 
 * @author SChoumiloff
 * @since 1.0
 */
public class OrderMisMatchException extends Exception {
    /**
     * Constructs a new {@code OrderMisMatchException} with the specified detail
     * message.
     *
     * @param errorMessage the detail message describing the validation error
     */
    public OrderMisMatchException(String errorMessage) {
        super(errorMessage);
    }
}
