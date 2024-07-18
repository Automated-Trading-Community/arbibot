package com.arbibot.api;

import com.arbibot.domain.ArbitrageInfo;

public interface Arbitrage {

    boolean isProfitable();

    boolean canArbitrate();

    void arbitrate(ArbitrageInfo arbitrageInfo);
}
