package com.arbibot.infra.adapter.forexchangecommunication.binance.exceptions;

public class BinancePermissionApiKeysException extends Exception {
    public BinancePermissionApiKeysException(String errorMessage) {
        super(errorMessage);
    }
}
