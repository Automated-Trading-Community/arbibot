package com.arbibot.domain.exceptions;

/**
 * The {@code ExchangeException} class represents an exception that is
 * thrown
 * when an error occurs during the initialization or use of a {@code Exchange}
 * object
 * <p>
 * This exception is used to indicate specific validation errors that occur
 * during the order validation process.
 * It extends the {@code Exception} class and provides a constructor to create
 * an instance with a custom error message.
 * </p>
 * <p>
 *
 * @see com.bat.domain.entities.Exchange
 * 
 * @author SChoumiloff
 * @since 1.0
 */
public class ExchangeException extends RuntimeException {

    /**
     * Constructs a new {@code PairException} with the specified detail
     * message.
     *
     * @param errorMessage the detail message describing the validation error
     */
    public ExchangeException(String errorMessage) {
        super(errorMessage);
    }
}