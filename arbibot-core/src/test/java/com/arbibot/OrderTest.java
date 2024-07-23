package com.arbibot;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

import com.arbibot.entities.Order;
import com.arbibot.entities.OrderType;
import com.arbibot.entities.Pair;
import com.arbibot.entities.Asset;

public class OrderTest {
    @Test
    public void testBuyOrder() {
        Pair pair = new Pair(new Asset("btc"), new Asset("usdt"));
        BigDecimal quantity = new BigDecimal("40000");
        BigDecimal currentPairPrice = new BigDecimal("40000");
        BigDecimal percentFees = new BigDecimal("0.1");

        Order order = new Order(pair, OrderType.BUY, quantity, Order.Reference.QUOTE, currentPairPrice, percentFees);

        assertTrue(order.getQttBaseAsset().equals(BigDecimal.valueOf(1)));
        assertTrue(order.getFees().equals(BigDecimal.valueOf(0.001)));
        assertTrue(order.getExexutedQuantityQuoteAsset().equals(BigDecimal.valueOf(39600)));
        assertTrue(order.getExexutedQuantityBaseAsset().equals(BigDecimal.valueOf(0.99)));
    }

    @Test
    public void testSellOrder() {
        Pair pair = new Pair(new Asset("btc"), new Asset("usdt"));
        BigDecimal quantity = new BigDecimal("1");
        BigDecimal currentPairPrice = new BigDecimal("40000");
        BigDecimal percentFees = new BigDecimal("0.1");

        Order order = new Order(pair, OrderType.SELL, quantity, Order.Reference.BASE, currentPairPrice, percentFees);

        assertTrue(order.getQttBaseAsset().equals(BigDecimal.valueOf(1)));
        assertTrue(order.getFees().equals(currentPairPrice.multiply(percentFees.divide(BigDecimal.valueOf(100)))));
        assertTrue(order.getExexutedQuantityQuoteAsset().equals(BigDecimal.valueOf(39600)));
        assertTrue(order.getExexutedQuantityBaseAsset().equals(BigDecimal.valueOf(0.99)));
    }
}
