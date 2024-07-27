package com.automated_trading_community.arbibot_infra.exchange.impl.binance.Exceptions;

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
