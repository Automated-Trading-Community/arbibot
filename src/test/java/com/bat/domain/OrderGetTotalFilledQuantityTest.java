package com.bat.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.bat.domain.exceptions.OrderMisMatchException;

public class OrderGetTotalFilledQuantityTest {

    private OrderStopLimit stopLimitOrder;
    private OrderLimit limitOrder;
    private OrderOco orderOco;
    private Pair btcUsdPair;

    @BeforeEach
    public void setUp() {
        Asset btc = Asset.create("BTC");
        Asset usd = Asset.create("USD");
        btcUsdPair = Pair.create(btc, usd);

        stopLimitOrder = new OrderStopLimit(
                "broker1",
                OrderAction.BUY,
                OrderType.STOP_LIMIT,
                btcUsdPair,
                Instant.now(),
                Instant.now(),
                null,
                null,
                new BigDecimal("1.0"),
                Arrays.asList(new BigDecimal("50000")),
                Arrays.asList(new BigDecimal("1.0")),
                btc,
                new BigDecimal("0.1"),
                new BigDecimal("50000"),
                new BigDecimal("51000"));
        stopLimitOrder.setStatus(OrderStatus.OPEN);

        limitOrder = new OrderLimit(
                "broker1",
                OrderAction.BUY,
                OrderType.LIMIT,
                btcUsdPair,
                Instant.now(),
                Instant.now(),
                null,
                null,
                new BigDecimal("1.0"),
                Arrays.asList(new BigDecimal("49000")),
                Arrays.asList(new BigDecimal("1.0")),
                btc,
                new BigDecimal("0.1"),
                new BigDecimal("49000"));
        limitOrder.setStatus(OrderStatus.OPEN);

        orderOco = new OrderOco(
                "broker1",
                OrderAction.BUY,
                OrderType.OCO,
                btcUsdPair,
                Instant.now(),
                Instant.now(),
                Instant.now(),
                Instant.now(),
                new BigDecimal("1.0"),
                Arrays.asList(new BigDecimal("50000")),
                Arrays.asList(new BigDecimal("1.0")),
                btc,
                new BigDecimal("0.1"),
                limitOrder,
                stopLimitOrder);
    }

    @Test
    public void testGetTotalFilledQuantityWhenOrderIsFilled() throws OrderMisMatchException {
        orderOco.setStatus(OrderStatus.EXECUTED);
        orderOco.setFilledQuantity(Arrays.asList(new BigDecimal("0.5"), new BigDecimal("0.5")));

        BigDecimal totalFilledQuantity = orderOco.getTotalFilledQuantity();
        assertEquals(new BigDecimal("1.0"), totalFilledQuantity);
    }

    @Test
    public void testGetTotalFilledQuantityWhenOrderIsOpen() {
        orderOco.setStatus(OrderStatus.OPEN);
        orderOco.setFilledQuantity(Arrays.asList(new BigDecimal("0.5"), new BigDecimal("0.5")));

        assertThrows(OrderMisMatchException.class, orderOco::getTotalFilledQuantity, "Order is not filled yet");
    }

    @Test
    public void testGetTotalFilledQuantityWithMismatch() {
        orderOco.setStatus(OrderStatus.EXECUTED);
        orderOco.setFilledQuantity(Collections.emptyList());

        assertThrows(OrderMisMatchException.class, orderOco::getTotalFilledQuantity,
                "Mismatch between the filled quantity and based quantity");
    }

    @Test
    public void testGetTotalFilledQuantityWhenFilledQuantityIsNull() {
        orderOco.setStatus(OrderStatus.EXECUTED);
        orderOco.setFilledQuantity(null);

        assertThrows(OrderMisMatchException.class, orderOco::getTotalFilledQuantity,
                "Mismatch between the filled quantity and based quantity");
    }
}