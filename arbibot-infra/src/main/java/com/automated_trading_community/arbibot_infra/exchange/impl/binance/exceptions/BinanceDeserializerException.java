package com.automated_trading_community.arbibot_infra.exchange.impl.binance.exceptions;

/**
 * 
 * A {@code BinanceDeserializerException} is thrown when an error occurs
 * during the deserialization of a JSON response from the Binance API.
 * 
 * @since 1.0
 * @author SChoumiloff SGuillemin
 */
public class BinanceDeserializerException extends Exception {
    /**
     * Constructs a new {@code BinanceDeserializerException} with the specified
     * detail message.
     *
     * @param errorMessage the detail message describing the deserialization error
     */
    public BinanceDeserializerException(String errorMessage) {
        super(errorMessage);
    }
}
