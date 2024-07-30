package com.arbibot.core.usecases.arbitrage;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.arbibot.core.entities.Asset;
import com.arbibot.core.entities.Exchange;
import com.arbibot.core.entities.ExchangeType;
import com.arbibot.core.entities.Order;
import com.arbibot.core.entities.OrderType;
import com.arbibot.core.entities.Pair;
import com.arbibot.core.exceptions.TriangularArbitragingException;
import com.arbibot.core.ports.output.ForExchangeCommunication;

public class TriangularArbitrageTest {
    /**
     * Stub used to perform test independently of the exchange.
     */
    private class ForExchangeCommunicationStub implements ForExchangeCommunication {
        private Map<Pair, BigDecimal> prices = new HashMap<>();
        private Map<Pair, Order> passedOrders = new HashMap<>();

        @Override
        public BigDecimal getPriceForPair(Pair pair, Exchange exchange) {
            if (prices.containsKey(pair)) {
                pair.setPrice(prices.get(pair));
            } else {
                pair.setPrice(null);
            }

            return pair.getPrice();
        }

        @Override
        public void passOrders(Order[] orders) {
            for (Order order : orders)
                passedOrders.put(order.getPair(), order);
        }

        public void setPrice(Pair pair, BigDecimal price) {
            prices.put(pair, price);
            pair.setPrice(price);
        }

        public Map<Pair, Order> getPassedOrders() {
            return passedOrders;
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

    private ForExchangeCommunicationStub forExchangeCommunicationStub;
    private Exchange exchange;
    private TriangularArbitrage triangularArbitrage;

    private Pair pair1;
    private Pair pair2;
    private Pair pair3;

    private Asset btc;
    private Asset eth;
    private Asset usdt;

    @BeforeEach
    public void setUp() {
        this.forExchangeCommunicationStub = new ForExchangeCommunicationStub();
        this.btc = new Asset("BTC");
        this.eth = new Asset("ETH");
        this.usdt = new Asset("USDT");

        this.pair1 = new Pair(eth, usdt);
        this.pair2 = new Pair(eth, btc);
        this.pair3 = new Pair(btc, usdt);

        this.exchange = new Exchange("Binance", "https://binance.com", BigDecimal.valueOf(0.1), ExchangeType.CEX);

        this.triangularArbitrage = new TriangularArbitrage(this.forExchangeCommunicationStub);
    }

    // @Test
    // public void testValidTriangle() {
    // this.forExchangeCommunicationStub.setPrice(pair1, BigDecimal.valueOf(1980));
    // this.forExchangeCommunicationStub.setPrice(pair2, BigDecimal.valueOf(0.05));
    // this.forExchangeCommunicationStub.setPrice(pair3, BigDecimal.valueOf(40000));
    // assertDoesNotThrow(() ->
    // triangularArbitrage.performTriangualarArbitrage(pair1, pair2, pair3,
    // exchange,
    // BigDecimal.valueOf(25)));
    // }

    @Test
    public void testNotValidTriangle() {
        TriangularArbitragingException error = assertThrows(TriangularArbitragingException.class,
                () -> this.triangularArbitrage.performTriangualarArbitrage(pair2,
                        pair1, pair3, this.exchange, BigDecimal.valueOf(20)));
        assertTrue(error.getMessage().equals("Triangle or asset buy is not valid"));
    }

    @Test
    public void testPricesNullAssert() {
        AssertionError error = assertThrows(AssertionError.class,
                () -> this.triangularArbitrage.performTriangualarArbitrage(pair1, pair2,
                        pair3,
                        this.exchange, BigDecimal.valueOf(20)));
        assertTrue(error.getMessage().equals(pair1.toString() + " price is null"));
    }

    @Test
    public void testPricesPair2NullAssert() {
        this.forExchangeCommunicationStub.setPrice(pair1,
                BigDecimal.valueOf(2503.4));
        AssertionError error = assertThrows(AssertionError.class,
                () -> this.triangularArbitrage.performTriangualarArbitrage(pair1, pair2,
                        pair3,
                        this.exchange, BigDecimal.valueOf(20)));
        assertTrue(error.getMessage().equals(pair2.toString() + " price is null"));
    }

    @Test
    public void testPricesPair3NullAssert() {
        this.forExchangeCommunicationStub.setPrice(pair1,
                BigDecimal.valueOf(2503.4));
        this.forExchangeCommunicationStub.setPrice(pair2, BigDecimal.valueOf(0.05));
        AssertionError error = assertThrows(AssertionError.class,
                () -> this.triangularArbitrage.performTriangualarArbitrage(pair1, pair2,
                        pair3,
                        this.exchange, BigDecimal.valueOf(20)));
        assertTrue(error.getMessage().equals(pair3.toString() + " price is null"));
    }

    @Test
    public void testPerformTriangularArbitrageNoOpportunities() throws TriangularArbitragingException {
        this.forExchangeCommunicationStub.setPrice(pair1,
                BigDecimal.valueOf(2000.0));
        this.forExchangeCommunicationStub.setPrice(pair2, BigDecimal.valueOf(0.05));
        this.forExchangeCommunicationStub.setPrice(pair3,
                BigDecimal.valueOf(40000));

        this.triangularArbitrage.performTriangualarArbitrage(pair1, pair2, pair3,
                this.exchange, BigDecimal.valueOf(20));
        assertTrue(this.forExchangeCommunicationStub.getPassedOrders().size() == 0);
    }

    @Test
    public void testPerformTriangularArbitrageOpportunitie() throws TriangularArbitragingException {
        this.forExchangeCommunicationStub.setPrice(pair1, BigDecimal.valueOf(1980));
        this.forExchangeCommunicationStub.setPrice(pair2, BigDecimal.valueOf(0.05));
        this.forExchangeCommunicationStub.setPrice(pair3, BigDecimal.valueOf(40000));

        this.triangularArbitrage.performTriangualarArbitrage(pair1, pair2, pair3,
                this.exchange, BigDecimal.valueOf(20));

        assertTrue(this.forExchangeCommunicationStub.getPassedOrders().size() == 3);
        assertTrue(this.forExchangeCommunicationStub.getPassedOrders().get(pair1).getType().equals(OrderType.BUY));
        assertTrue(this.forExchangeCommunicationStub.getPassedOrders().get(pair2).getType().equals(OrderType.SELL));
        assertTrue(this.forExchangeCommunicationStub.getPassedOrders().get(pair3).getType().equals(OrderType.SELL));

        // TODO tester si les ordres ont été rempli mais je ne vois pas trop comment
        // tester cette fonctionnalité...
    }
}
