package com.arbibot.usecases;

import java.math.BigDecimal;

import com.arbibot.entities.Asset;
import com.arbibot.entities.Exchange;
import com.arbibot.ports.input.ForTriangularArbitraging;

public class TriangularArbitrage implements ForTriangularArbitraging {
    // private ExchangeDataRecovery exchangeDataRecovery;

    @Override
    public boolean isProfitable() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isProfitable'");
    }

    @Override
    public boolean canArbitrate() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'canArbitrate'");
    }

    @Override
    public void performTriangualarArbitrage(Asset asset1, Asset asset2, Asset asset3, Exchange exchange,
            BigDecimal quentity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'performTriangualarArbitrage'");
    }
}