package com.arbibot.ports.input;

/**
 * Super interface qui ne va pas être implémenter directement.
 */
public interface ForArbitraging {

    /**
     * Indicates whether the arbitrage action is potentially profitable. 
     * 
     * @return {@code boolean}
     */
    boolean isProfitable();

    /**
     * Indicates whether a trade can be operated.
     * 
     * @return {@code boolean}
     */
    boolean canArbitrate();
}
