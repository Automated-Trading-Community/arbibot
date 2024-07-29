package com.automated_trading_community.arbibot_infra.exchange.impl.binance.models;

import lombok.Getter;

public enum BinanceEventType {
    EXECUTION_REPORT("executionReport"),
    OUTBOUND_ACCOUNT_POSITION("outboundAccountPosition");

    @Getter
    private String type;
    
    BinanceEventType(String type) {
        this.type = type;
    }

    /**
     * Returns the BinanceEventType corresponding to the given type string.
     *
     * @param type the type string to match against
     * @return the corresponding BinanceEventType, or throws an
     *         IllegalArgumentException if no match is found
     * @throws IllegalArgumentException if no match is found
     */
    public static BinanceEventType fromType(String type) throws IllegalArgumentException {
        for (BinanceEventType eventType : values()) {
            if (eventType.type.equals(type)) {
                return eventType;
            }
        }
        throw new IllegalArgumentException("Unknown type: " + type);
    }
}