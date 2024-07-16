package com.bat.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bat.domain.exceptions.OrderValidationException;

class OrderLimitTest {

    private Asset assetBTC;
    private Asset assetUSD;
    private Pair tradingPair;
    private OrderAction actionBuy;

    @BeforeEach
    void setUp() {
        assetBTC = Asset.create("BTC");
        assetUSD = Asset.create("USD");
        tradingPair = Pair.create(assetBTC, assetUSD);
        actionBuy = OrderAction.BUY;
    }

    @Test
    void testConstructorFromDatabase() {
        String brokerId = "broker123";
        OrderAction action = actionBuy;
        OrderType type = OrderType.LIMIT;
        Instant creationTime = Instant.now();
        Instant openTime = Instant.now();
        Instant filledTime = Instant.now();
        Instant canceledTime = Instant.now();
        BigDecimal quantity = BigDecimal.valueOf(1.5);
        List<BigDecimal> filledPrice = List.of(BigDecimal.valueOf(30000));
        List<BigDecimal> filledQuantity = List.of(BigDecimal.valueOf(1.5));
        Asset feeAsset = Asset.create("USD");
        BigDecimal fees = BigDecimal.valueOf(10);
        BigDecimal limitPrice = BigDecimal.valueOf(35000);

        OrderLimit order = new OrderLimit(brokerId, action, type, tradingPair, creationTime, openTime, filledTime,
                canceledTime,
                quantity, filledPrice, filledQuantity, feeAsset, fees, limitPrice);

        assertEquals(brokerId, order.getBrokerId());
        assertEquals(action, order.getAction());
        assertEquals(type, order.getType());
        assertEquals(tradingPair, order.getTradingPair());
        assertEquals(creationTime, order.getOrderCreationTimestamp());
        assertEquals(openTime, order.getOrderOpenTimestamp());
        assertEquals(filledTime, order.getOrderFilledTimestamp());
        assertEquals(canceledTime, order.getOrderCanceledTimestamp());
        assertEquals(quantity, order.getQuantity());
        assertEquals(filledPrice, order.getFilledPrice());
        assertEquals(filledQuantity, order.getFilledQuantity());
        assertEquals(feeAsset, order.getFeeAsset());
        assertEquals(fees, order.getFees());
        assertEquals(limitPrice, order.getLimitPrice());
    }

    @Test
    void testConstructorInMemory() {
        BigDecimal quantity = BigDecimal.valueOf(2.0);
        BigDecimal limitPrice = BigDecimal.valueOf(2500);

        OrderLimit order = new OrderLimit(actionBuy, tradingPair, quantity, limitPrice);

        assertEquals(actionBuy, order.getAction());
        assertEquals(tradingPair, order.getTradingPair());
        assertEquals(quantity, order.getQuantity());
        assertEquals(limitPrice, order.getLimitPrice());
    }

    @Test
    void testValidateOrder_ThrowsExceptionForNullLimitPrice() {
        BigDecimal quantity = BigDecimal.valueOf(1.0);
        OrderLimit order = new OrderLimit(actionBuy, tradingPair, quantity, null);

        OrderValidationException exception = assertThrows(OrderValidationException.class, () -> order.validateOrder());
        assertEquals("limitPrice cannot be null or zero", exception.getMessage());
    }

    @Test
    void testValidateOrder_ThrowsExceptionForZeroLimitPrice() {
        BigDecimal quantity = BigDecimal.valueOf(1.0);
        OrderLimit order = new OrderLimit(actionBuy, tradingPair, quantity, BigDecimal.ZERO);

        OrderValidationException exception = assertThrows(OrderValidationException.class, () -> order.validateOrder());
        assertEquals("limitPrice cannot be null or zero", exception.getMessage());
    }

    @Test
    void testValidateOrder_DoesNotThrowException() {
        BigDecimal quantity = BigDecimal.valueOf(1.0);
        BigDecimal limitPrice = BigDecimal.valueOf(35000);
        OrderLimit order = new OrderLimit(actionBuy, tradingPair, quantity, limitPrice);

        assertDoesNotThrow(() -> order.validateOrder());
    }

    @Test
    void testValidateOrder_TypeNull() {
        BigDecimal quantity = BigDecimal.valueOf(1.0);
        OrderLimit order = new OrderLimit(null, tradingPair, quantity, BigDecimal.ZERO);

        OrderValidationException exception = assertThrows(OrderValidationException.class, () -> order.validateOrder());
        assertEquals("limitPrice cannot be null or zero", exception.getMessage());
    }
}
