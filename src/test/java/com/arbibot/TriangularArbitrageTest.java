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

        btcusdt = new Pair(btc, usdt);
        ethusdt = new Pair(eth, usdt);
        ethbtc = new Pair(eth, btc);

        cexPairs = Arrays.asList(btcusdt, ethusdt, ethbtc);
        this.exchange = new Exchange("Binance", "https://binance.com", cexPairs, BigDecimal.valueOf(0.1), usdt,
                ExchangeType.CEX);
        pair1 = new Pair(eth, usdt);
        pair2 = new Pair(eth, btc);
        pair3 = new Pair(btc, usdt);

        triangularArbitrage = new TriangularArbitrage(forExchangeDataRecoveryStub, exchange, exchange.getFeesAsset());
    }

    @Test
    public void testPerformTriangularArbitrage() throws TriangularArbitragingException {
        forExchangeDataRecoveryStub.setPrice(pair1, BigDecimal.valueOf(40000));
        forExchangeDataRecoveryStub.setPrice(pair2, BigDecimal.valueOf(0.05));
        forExchangeDataRecoveryStub.setPrice(pair3, BigDecimal.valueOf(3000));
        forExchangeDataRecoveryStub.setPrice(new Pair(pair2.getBaseAsset(), usdt), BigDecimal.valueOf(3000));
        triangularArbitrage.performTriangualarArbitrage(pair1, pair2, pair3, exchange, BigDecimal.valueOf(1),
                BigDecimal.valueOf(1000));
        assertEquals(0, forExchangeDataRecoveryStub.getPassedOrders().size());
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
