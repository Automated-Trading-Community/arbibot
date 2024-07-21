package com.arbibot.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.Test;

public class OrderTest {

    @Test
    public void testOrderCreationWithQuoteAsset() {
        Asset baseAsset = Asset.create("BTC");
        Asset quoteAsset = Asset.create("USD");
        Pair pair = Pair.create(baseAsset, quoteAsset);
        BigDecimal qttQuoteAsset = BigDecimal.valueOf(1000);
        BigDecimal currentPairPrice = BigDecimal.valueOf(50000);
        BigDecimal percentFees = BigDecimal.valueOf(0.1);

        Order order = new Order(pair, OrderType.BUY, qttQuoteAsset, null, currentPairPrice, percentFees);

        assertEquals(pair, order.getPair());
        assertEquals(OrderType.BUY, order.getType());
        assertEquals(qttQuoteAsset, order.getQttQuoteAsset());
        assertEquals(qttQuoteAsset.multiply(currentPairPrice), order.getQttBaseAsset());
        assertEquals(currentPairPrice, order.getCurrentPairPrice());
        assertEquals(percentFees, order.getPercentFees());
    }

    @Test
    public void testOrderCreationWithBaseAsset() {
        Asset baseAsset = Asset.create("BTC");
        Asset quoteAsset = Asset.create("USD");
        Pair pair = Pair.create(baseAsset, quoteAsset);
        BigDecimal qttBaseAsset = BigDecimal.valueOf(0.02);
        BigDecimal currentPairPrice = BigDecimal.valueOf(50000);
        BigDecimal percentFees = BigDecimal.valueOf(0.1);

        Order order = new Order(pair, OrderType.SELL, null, qttBaseAsset, currentPairPrice, percentFees);

        assertEquals(pair, order.getPair());
        assertEquals(OrderType.SELL, order.getType());
        assertEquals(qttBaseAsset, order.getQttBaseAsset());
        assertEquals(qttBaseAsset.divide(currentPairPrice), order.getQttQuoteAsset());
        assertEquals(currentPairPrice, order.getCurrentPairPrice());
        assertEquals(percentFees, order.getPercentFees());
    }

    @Test
    public void testOrderCreationWithNullAssets() {
        Asset baseAsset = Asset.create("BTC");
        Asset quoteAsset = Asset.create("USD");
        Pair pair = Pair.create(baseAsset, quoteAsset);
        BigDecimal currentPairPrice = BigDecimal.valueOf(50000);
        BigDecimal percentFees = BigDecimal.valueOf(0.1);

        assertThrows(IllegalArgumentException.class, () -> {
            new Order(pair, OrderType.BUY, null, null, currentPairPrice, percentFees);
        });
    }

    @Test
    public void testComputeFees() {
        Asset baseAsset = Asset.create("BTC");
        Asset quoteAsset = Asset.create("USD");
        Pair pair = Pair.create(baseAsset, quoteAsset);
        BigDecimal qttQuoteAsset = BigDecimal.valueOf(1000);
        BigDecimal currentPairPrice = BigDecimal.valueOf(50000);
        BigDecimal percentFees = BigDecimal.valueOf(0.1);

        Order order = new Order(pair, OrderType.BUY, qttQuoteAsset, null, currentPairPrice, percentFees);

        BigDecimal expectedFeesBaseAsset = order.getQttBaseAsset()
                .multiply(percentFees.divide(BigDecimal.valueOf(100)));
        BigDecimal expectedFeesQuoteAsset = order.getQttQuoteAsset()
                .multiply(percentFees.divide(BigDecimal.valueOf(100)));

        assertEquals(expectedFeesBaseAsset, order.getExexutedQuantityBaseAsset());
        assertEquals(expectedFeesQuoteAsset, order.getExexutedQuantityQuoteAsset());
    }
}
