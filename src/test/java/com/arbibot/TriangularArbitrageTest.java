package com.arbibot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.arbibot.entities.Asset;
import com.arbibot.entities.Exchange;
import com.arbibot.entities.ExchangeType;
import com.arbibot.entities.Order;
import com.arbibot.entities.Pair;
import com.arbibot.ports.output.ForExchangeDataRecovery;
import com.arbibot.usecases.arbitrage.TriangularArbitrage;
import com.arbibot.usecases.arbitrage.exceptions.TriangularArbitragingException;

public class TriangularArbitrageTest {

    private ForExchangeDataRecoveryStub forExchangeDataRecoveryStub;
    private Exchange exchange;
    private TriangularArbitrage triangularArbitrage;

    private Asset buyCurrency;
    private Pair pair1;
    private Pair pair2;
    private Pair pair3;

    private Asset btc;
    private Asset eth;
    private Asset usdt;

    private Pair btcusdt;
    private Pair ethusdt;
    private Pair ethbtc;

    private List<Pair> cexPairs;

    @Before
    public void setUp() {
        this.forExchangeDataRecoveryStub = new ForExchangeDataRecoveryStub();
        btc = Asset.create("BTC");
        eth = Asset.create("ETH");
        usdt = Asset.create("USDT");

        btcusdt = Pair.create(btc, usdt);
        ethusdt = Pair.create(eth, usdt);
        ethbtc = Pair.create(eth, btc);

        cexPairs = Arrays.asList(btcusdt, ethusdt, ethbtc);
        this.exchange = new Exchange("Binance", "https://binance.com", cexPairs, BigDecimal.valueOf(0.1), usdt,
                ExchangeType.CEX);
        buyCurrency = Asset.create("USD");
        Asset asset1 = Asset.create("BTC");
        Asset asset2 = Asset.create("ETH");

        pair1 = Pair.create(asset1, buyCurrency);
        pair2 = Pair.create(asset1, asset2);
        pair3 = Pair.create(asset2, buyCurrency);

        triangularArbitrage = new TriangularArbitrage(forExchangeDataRecoveryStub, exchange, buyCurrency);
    }

    @Test
    public void testPerformOportunity() {
        assertEquals(true, true);
    }

    private class ForExchangeDataRecoveryStub implements ForExchangeDataRecovery {

        private Map<Pair, BigDecimal> prices = new HashMap<>();
        private Map<Pair, Order> passedOrders = new HashMap<>();

        @Override
        public BigDecimal getPriceForPair(Pair pair) {
            return prices.get(pair);
        }

        @Override
        public void passOrder(Order order) {
            passedOrders.put(order.getPair(), order);
        }

        public void setPrice(Pair pair, BigDecimal price) {
            prices.put(pair, price);
        }

        public Map<Pair, Order> getPassedOrders() {
            return passedOrders;
        }

    }
}
