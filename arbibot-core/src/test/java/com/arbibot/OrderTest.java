package com.arbibot;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.arbibot.entities.Order;
import com.arbibot.entities.OrderType;
import com.arbibot.entities.Pair;
import com.arbibot.entities.Asset;

public class OrderTest {
    @Test
    public void testBuyOrder() {
        Pair pair = new Pair(new Asset("btc"), new Asset("usdt"));
        pair.setPrice(new BigDecimal("40000"));
        BigDecimal quantity = new BigDecimal("40000");
        BigDecimal percentFees = new BigDecimal("0.1");

        Order order = new Order(pair, OrderType.BUY, quantity, percentFees);

        assertTrue(order.getQttBaseAsset().equals(BigDecimal.valueOf(1)));
        assertTrue(order.getFees().equals(BigDecimal.valueOf(0.001)));
        assertTrue(order.getExecutedQuantityQuoteAsset().equals(BigDecimal.valueOf(39600)));
        assertTrue(order.getExecutedQuantityBaseAsset().equals(BigDecimal.valueOf(0.99)));
    }

    @Test
    public void testSellOrder() {
        Pair pair = new Pair(new Asset("btc"), new Asset("usdt"));
        pair.setPrice(new BigDecimal("40000"));
        BigDecimal quantity = new BigDecimal("1");
        BigDecimal percentFees = new BigDecimal("0.1");

        Order order = new Order(pair, OrderType.SELL, quantity, percentFees);

        assertTrue(order.getQttBaseAsset().equals(BigDecimal.valueOf(1)));
        assertTrue(order.getFees().equals(pair.getPrice().multiply(percentFees.divide(BigDecimal.valueOf(100)))));
        assertTrue(order.getExecutedQuantityQuoteAsset().equals(BigDecimal.valueOf(39600)));
        assertTrue(order.getExecutedQuantityBaseAsset().equals(BigDecimal.valueOf(0.99)));
    }
}
