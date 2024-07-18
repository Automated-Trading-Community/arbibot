package com.arbibot.exceptions;

/**
 * The {@code PairException} class represents an exception that is
 * thrown
 * when an error occurs during the initialization or use of a {@code Pair}
 * object
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
 * } catch (PairException e) {
 *     System.out.println("Validation error: " + e.getMessage());
 *     // Handle the validation error, such as logging or notifying the user
 * }
 * }
 * </pre>
 *
 * @see com.arbibot.entities.bat.entities.Pair
 * 
 * @author SChoumiloff
 * @since 1.0
 */
public class PairException extends RuntimeException {

    /**
     * Constructs a new {@code PairException} with the specified detail
     * message.
     *
     * @param errorMessage the detail message describing the validation error
     */
    public PairException(String errorMessage) {
        super(errorMessage);
    }
}