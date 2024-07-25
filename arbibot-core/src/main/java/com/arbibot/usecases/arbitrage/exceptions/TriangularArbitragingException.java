package com.arbibot.usecases.arbitrage.exceptions;

/**
 * Error thrown if an error occurs when performing triangular arbitrage.
 * 
 * @see com.arbibot.usecases.arbitrage.TriangularArbitrage
 * 
 * @author SChoumiloff
 * @author SebastienGuillemin
 * @since 1.0
 * 
 */
public class TriangularArbitragingException extends Exception {
    /**
     *
     * @param errorMessage the detail message describing the validation error
     */
    public TriangularArbitragingException(String errorMessage) {
        super(errorMessage);
    }
}
