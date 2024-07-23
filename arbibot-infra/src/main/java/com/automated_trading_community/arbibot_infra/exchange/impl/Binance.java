package com.automated_trading_community.arbibot_infra.exchange.impl;

import java.math.BigDecimal;
import java.util.List;

import com.arbibot.entities.Asset;
import com.arbibot.entities.Exchange;
import com.arbibot.entities.Order;
import com.arbibot.entities.Pair;
import com.arbibot.ports.output.ForExchangeCommunication;

public class Binance implements ForExchangeCommunication {

    @Override
    public void getPriceForPair(Pair pair, Exchange exchange) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPriceForPair'");
    }

    @Override
    public void passOrders(Order[] orders) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'passOrders'");
    }

    @Override
    public BigDecimal getBalanceForAsset(Asset asset, Exchange exchange) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBalanceForAsset'");
    }

    @Override
    public Order getOrderInfo(String orderId, Exchange exchange) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getOrderInfo'");
    }

    @Override
    public List<Pair> getAvailableTradingPairs(Exchange exchange) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAvailableTradingPairs'");
    }
    
}
