package com.arbibot.infra.adapter.forexchangecommunication.binance.exceptions;

public class BinanceWrongPermissions extends Exception {
    public BinanceWrongPermissions(String errorMessage) {
        super(errorMessage);
    }
}
