package com.arbibot.core.exceptions;

/**
 * The {@code BarException} class represents an exception that is thrown
 * when an error occurs during the processing of create or update operations on
 * a bar.
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
 *     bar.validateBar()
 * } catch (BarException e) {
 *     System.out.println("Validation error: " + e.getMessage());
 *     // Handle the validation error, such as logging or notifying the user
 * }
 * }
 * </pre>
 *
 * @see com.bat.core.entities.Bar#validateBar()
 * @author SChoumiloff
 * @since 1.0
 */
public class BarException extends Exception {
    /**
     * Constructs a new {@code BarException} with the specified detail message.
     *
     * @param errorMessage the detail message describing the validation error
     */
    public BarException(String errorMessage) {
        super(errorMessage);
    }
}