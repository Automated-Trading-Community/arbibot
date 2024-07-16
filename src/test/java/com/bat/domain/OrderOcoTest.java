package com.bat.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bat.domain.exceptions.OrderValidationException;

public class OrderOcoTest {

    private OrderStopLimit stopLimitOrderBuy;
    private OrderLimit limitOrderBuy;
    private OrderOco orderOcoBuy;
    private OrderStopLimit stopLimitOrderSell;
    private OrderLimit limitOrderSell;
    private OrderOco orderOcoSell;
    private Pair btcUsdPair;

    @BeforeEach
    public void setUp() {
        Asset btc = Asset.create("BTC");
        Asset usd = Asset.create("USD");
        btcUsdPair = Pair.create(btc, usd);

        stopLimitOrderBuy = new OrderStopLimit(
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

        limitOrderBuy = new OrderLimit(
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

        orderOcoBuy = new OrderOco(
                OrderAction.BUY,
                btcUsdPair,
                new BigDecimal("1.0"),
                stopLimitOrderBuy,
                limitOrderBuy);

        stopLimitOrderSell = new OrderStopLimit(
                "broker2",
                OrderAction.SELL,
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
                new BigDecimal("51000"),
                new BigDecimal("50000"));

        limitOrderSell = new OrderLimit(
                "broker2",
                OrderAction.SELL,
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
                new BigDecimal("51000"));

        orderOcoSell = new OrderOco(
                OrderAction.SELL,
                btcUsdPair,
                new BigDecimal("1.0"),
                stopLimitOrderSell,
                limitOrderSell);
    }

    @Test
    public void testOrderOcoInitializationBuy() {
        assertNotNull(orderOcoBuy);
        assertEquals(OrderAction.BUY, orderOcoBuy.getAction());
        assertEquals(btcUsdPair, orderOcoBuy.getTradingPair());
        assertEquals(stopLimitOrderBuy, orderOcoBuy.getSecondaryOrder());
        assertEquals(limitOrderBuy, orderOcoBuy.getPrimaryOrder());
    }

    @Test
    public void testOrderOcoInitializationSell() {
        assertNotNull(orderOcoSell);
        assertEquals(OrderAction.SELL, orderOcoSell.getAction());
        assertEquals(btcUsdPair, orderOcoSell.getTradingPair());
        assertEquals(stopLimitOrderSell, orderOcoSell.getSecondaryOrder());
        assertEquals(limitOrderSell, orderOcoSell.getPrimaryOrder());
    }

    @Test
    public void testOrderValidationSuccessBuy() throws OrderValidationException {
        limitOrderBuy.setStatus(OrderStatus.OPEN);
        stopLimitOrderBuy.setStatus(OrderStatus.OPEN);

        orderOcoBuy.validateOrder();
    }

    @Test
    public void testOrderValidationSuccessSell() throws OrderValidationException {
        limitOrderSell.setStatus(OrderStatus.OPEN);
        stopLimitOrderSell.setStatus(OrderStatus.OPEN);

        orderOcoSell.validateOrder();
    }

    @Test
    public void testOrderValidationFailureDifferentActionsBuy() {
        stopLimitOrderBuy.setAction(OrderAction.SELL);

        OrderValidationException exception = assertThrows(OrderValidationException.class, () -> {
            orderOcoBuy.validateOrder();
        });

        assertEquals("Primary and secondary orders must have the same action", exception.getMessage());
    }

    @Test
    public void testOrderValidationFailureDifferentActionsSell() {
        stopLimitOrderSell.setAction(OrderAction.BUY);

        OrderValidationException exception = assertThrows(OrderValidationException.class, () -> {
            orderOcoSell.validateOrder();
        });

        assertEquals("Primary and secondary orders must have the same action", exception.getMessage());
    }

    @Test
    public void testOrderValidationFailureDifferentTradingPairsBuy() {
        Asset eth = Asset.create("ETH");
        Pair ethUsdPair = Pair.create(eth, Asset.create("USD"));
        stopLimitOrderBuy.setTradingPair(ethUsdPair);

        OrderValidationException exception = assertThrows(OrderValidationException.class, () -> {
            orderOcoBuy.validateOrder();
        });

        assertEquals("Primary and secondary orders must have the same trading pair", exception.getMessage());
    }

    @Test
    public void testOrderValidationFailureDifferentTradingPairsSell() {
        Asset eth = Asset.create("ETH");
        Pair ethUsdPair = Pair.create(eth, Asset.create("USD"));
        stopLimitOrderSell.setTradingPair(ethUsdPair);

        OrderValidationException exception = assertThrows(OrderValidationException.class, () -> {
            orderOcoSell.validateOrder();
        });

        assertEquals("Primary and secondary orders must have the same trading pair", exception.getMessage());
    }

    @Test
    public void testOrderValidationFailurePrimaryOpenSecondaryNotBuy() {
        limitOrderBuy.setStatus(OrderStatus.OPEN);
        stopLimitOrderBuy.setStatus(OrderStatus.CANCELED);

        OrderValidationException exception = assertThrows(OrderValidationException.class, () -> {
            orderOcoBuy.validateOrder();
        });

        assertEquals("Primary order cannot be open while secondary not", exception.getMessage());
    }

    @Test
    public void testOrderValidationFailurePrimaryOpenSecondaryNotSell() {
        limitOrderSell.setStatus(OrderStatus.OPEN);
        stopLimitOrderSell.setStatus(OrderStatus.CANCELED);

        OrderValidationException exception = assertThrows(OrderValidationException.class, () -> {
            orderOcoSell.validateOrder();
        });

        assertEquals("Primary order cannot be open while secondary not", exception.getMessage());
    }

    @Test
    public void testOrderValidationFailureSecondaryOpenPrimaryNotBuy() {
        limitOrderBuy.setStatus(OrderStatus.CANCELED);
        stopLimitOrderBuy.setStatus(OrderStatus.OPEN);

        OrderValidationException exception = assertThrows(OrderValidationException.class, () -> {
            orderOcoBuy.validateOrder();
        });

        assertEquals("Secondary order cannot be open while primary not", exception.getMessage());
    }

    @Test
    public void testOrderValidationFailureSecondaryOpenPrimaryNotSell() {
        limitOrderSell.setStatus(OrderStatus.CANCELED);
        stopLimitOrderSell.setStatus(OrderStatus.OPEN);

        OrderValidationException exception = assertThrows(OrderValidationException.class, () -> {
            orderOcoSell.validateOrder();
        });

        assertEquals("Secondary order cannot be open while primary not", exception.getMessage());
    }

    @Test
    public void testValidateBuyOrder() throws OrderValidationException {
        orderOcoBuy.validateBuyOrder();
    }

    @Test
    public void testValidateSellOrder() throws OrderValidationException {
        orderOcoSell.validateSellOrder();
    }

    @Test
    public void testConstructorPrimaryCanceledSecondaryExecuted() {
        Asset btc = Asset.create("BTC");
        Asset usd = Asset.create("USD");
        Pair btcUsdPair = Pair.create(btc, usd);

        OrderStopLimit stopLimitOrder = new OrderStopLimit(
                "broker1",
                OrderAction.BUY,
                OrderType.STOP_LIMIT,
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
                new BigDecimal("50000"),
                new BigDecimal("51000"));
        stopLimitOrder.setStatus(OrderStatus.EXECUTED);

        OrderLimit limitOrder = new OrderLimit(
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
        limitOrder.setStatus(OrderStatus.CANCELED);

        OrderOco orderOco = new OrderOco(
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

        assertEquals(stopLimitOrder.getQuantity(), orderOco.getQuantity());
    }

    @Test
    public void testConstructorSecondaryCanceledPrimaryExecuted() {
        Asset btc = Asset.create("BTC");
        Asset usd = Asset.create("USD");
        Pair btcUsdPair = Pair.create(btc, usd);

        OrderStopLimit stopLimitOrder = new OrderStopLimit(
                "broker1",
                OrderAction.BUY,
                OrderType.STOP_LIMIT,
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
                new BigDecimal("50000"),
                new BigDecimal("51000"));
        stopLimitOrder.setStatus(OrderStatus.CANCELED);

        OrderLimit limitOrder = new OrderLimit(
                "broker1",
                OrderAction.BUY,
                OrderType.LIMIT,
                btcUsdPair,
                Instant.now(),
                Instant.now(),
                Instant.now(),
                Instant.now(),
                new BigDecimal("1.0"),
                Arrays.asList(new BigDecimal("49000")),
                Arrays.asList(new BigDecimal("1.0")),
                btc,
                new BigDecimal("0.1"),
                new BigDecimal("49000"));
        limitOrder.setStatus(OrderStatus.EXECUTED);

        OrderOco orderOco = new OrderOco(
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

        assertEquals(limitOrder.getQuantity(), orderOco.getQuantity());
    }

    @Test
    public void testConstructorBothOrdersOpen() {
        Asset btc = Asset.create("BTC");
        Asset usd = Asset.create("USD");
        Pair btcUsdPair = Pair.create(btc, usd);

        OrderStopLimit stopLimitOrder = new OrderStopLimit(
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

        OrderLimit limitOrder = new OrderLimit(
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

        OrderOco orderOco = new OrderOco(
                "broker1",
                OrderAction.BUY,
                OrderType.OCO,
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
                limitOrder,
                stopLimitOrder);

        assertNull(orderOco.getQuantity());
    }

    @Test
    public void testConstructorBothOrdersCanceled() {
        Asset btc = Asset.create("BTC");
        Asset usd = Asset.create("USD");
        Pair btcUsdPair = Pair.create(btc, usd);

        OrderStopLimit stopLimitOrder = new OrderStopLimit(
                "broker1",
                OrderAction.BUY,
                OrderType.STOP_LIMIT,
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
                new BigDecimal("50000"),
                new BigDecimal("51000"));
        stopLimitOrder.setStatus(OrderStatus.CANCELED);

        OrderLimit limitOrder = new OrderLimit(
                "broker1",
                OrderAction.BUY,
                OrderType.LIMIT,
                btcUsdPair,
                Instant.now(),
                Instant.now(),
                Instant.now(),
                Instant.now(),
                new BigDecimal("1.0"),
                Arrays.asList(new BigDecimal("49000")),
                Arrays.asList(new BigDecimal("1.0")),
                btc,
                new BigDecimal("0.1"),
                new BigDecimal("49000"));
        limitOrder.setStatus(OrderStatus.CANCELED);

        OrderOco orderOco = new OrderOco(
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

        assertNull(orderOco.getQuantity());
    }
}