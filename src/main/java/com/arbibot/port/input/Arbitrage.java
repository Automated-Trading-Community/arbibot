package com.arbibot.port.input;

import com.arbibot.entities.ArbitrageInfo;

/**
 * 
 */
public interface Arbitrage {

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

    /**
     * Execute arbitrage.
     * 
     * @param arbitrageInfo
     */
    void arbitrate(ArbitrageInfo arbitrageInfo);
}
