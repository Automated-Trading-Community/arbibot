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
        pair.setPrice(BigDecimal.valueOf(40000.0));
        BigDecimal quantity = BigDecimal.valueOf(40000.0);
        BigDecimal percentFees = BigDecimal.valueOf(0.1);

        Order order = new Order(pair, OrderType.BUY, quantity, percentFees);

        assertTrue(order.getQuantity().compareTo(BigDecimal.valueOf(40000.0)) == 0);
        assertTrue(order.getFees().compareTo(BigDecimal.valueOf(0.001)) == 0);
        assertTrue(order.getExecutedQuantity().compareTo(BigDecimal.valueOf(0.999)) == 0);
    }

    @Test
    public void testSellOrder() {
        Pair pair = new Pair(new Asset("btc"), new Asset("usdt"));
        pair.setPrice(BigDecimal.valueOf(40000.0));
        BigDecimal quantity = BigDecimal.valueOf(1.0);
        BigDecimal percentFees = BigDecimal.valueOf(0.1);

        Order order = new Order(pair, OrderType.SELL, quantity, percentFees);

        assertTrue(order.getQuantity().compareTo(BigDecimal.valueOf(1.0)) == 0);
        assertTrue(order.getFees().compareTo(BigDecimal.valueOf(40.0)) == 0); 
        assertTrue(order.getExecutedQuantity().compareTo(BigDecimal.valueOf(39960)) == 0);
    }
}