package com.arbibot.infra.adapter.forexchangecommunication.binance.exceptions;

public class BinanceWrongApiKeys extends Exception {

    
    public BinanceWrongApiKeys(String errorMessage) {
        super(errorMessage);
    }
}
