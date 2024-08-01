package com.arbibot.infra.adapter.forexchangecommunication.binance.exceptions;

/**
 * A {@code BinancePermissionApiKeysException} is thrown when the Binance API
 * keys has not enough permissions to execute orders and stuffs.
 *
 * @since 1.0
 * @author SChoumiloff SGuillemin
 */
public class BinancePermissionApiKeysException extends Exception {
    /**
     * Constructs a new {@code BinancePermissionApiKeysException} with the specified
     * detail message.
     *
     * @param errorMessage the detail message describing the permission error
     */
    public BinancePermissionApiKeysException(String errorMessage) {
        super(errorMessage);
    }
}
