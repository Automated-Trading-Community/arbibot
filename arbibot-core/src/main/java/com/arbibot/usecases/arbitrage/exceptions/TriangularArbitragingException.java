package com.arbibot.usecases.arbitrage.exceptions;

public class TriangularArbitragingException extends Exception {
    /**
     * Constructs a new {@code TriangularArbitragingException} with the specified
     * detail
     * message.
     *
     * @param errorMessage the detail message describing the validation error
     */
    public TriangularArbitragingException(String errorMessage) {
        super(errorMessage);
    }
}
