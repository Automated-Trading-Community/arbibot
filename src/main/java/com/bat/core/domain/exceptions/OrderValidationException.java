package com.bat.core.domain.exceptions;

/**
 * The {@code OrderValidationException} class represents an exception that is
 * thrown
 * when an order fails to meet the validation criteria defined in the
 * {@code validateOrder} method.
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
 *     order.validateOrder();
 * } catch (OrderValidationException e) {
 *     System.out.println("Validation error: " + e.getMessage());
 *     // Handle the validation error, such as logging or notifying the user
 * }
 * }
 * </pre>
 * </p>
 *
 * @see com.bat.core.domain.entities.Order#validateOrder()
 * 
 * @author SChoumiloff
 * @since 1.0
 */
public class OrderValidationException extends Exception {

    /**
     * Constructs a new {@code OrderValidationException} with the specified detail
     * message.
     *
     * @param errorMessage the detail message describing the validation error
     */
    public OrderValidationException(String errorMessage) {
        super(errorMessage);
    }
}
