package com.arbibot.infra.adapter.forexchangecommunication.binance.exceptions;

/**
 * A {@code BinanceWrongApiKeys} is thrown when the Binance API keys are wrong
 * or missing.
 * BinanceClientException codeError -1022
 *
 * @since 1.0
 * @author SChoumiloff SGuillemin
 */
public class BinanceWrongApiKeys extends Exception {

    /**
     * Constructs a new {@code BinanceWrongApiKeys} with the specified
     * detail message.
     *
     * @param errorMessage the detail message describing the apiKey error
     */
    public BinanceWrongApiKeys(String errorMessage) {
        super(errorMessage);
    }
}
