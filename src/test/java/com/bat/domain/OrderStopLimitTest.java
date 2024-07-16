package com.bat.domain;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bat.domain.exceptions.OrderValidationException;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

class OrderStopLimitTest {

    private Asset assetBTC;
    private Asset assetUSD;
    private Pair tradingPair;
    private OrderAction actionBuy;
    private OrderAction actionSell;

    @BeforeEach
    void setUp() {
        assetBTC = Asset.create("BTC");
        assetUSD = Asset.create("USD");
        tradingPair = Pair.create(assetBTC, assetUSD);
        actionBuy = OrderAction.BUY;
        actionSell = OrderAction.SELL;
    }

    @Test
    void testConstructorFromDatabase() {
        String brokerId = "broker123";
        OrderType type = OrderType.STOP_LIMIT;
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
        BigDecimal stopPrice = BigDecimal.valueOf(34000);

        OrderStopLimit order = new OrderStopLimit(brokerId, actionBuy, type, tradingPair, creationTime, openTime,
                filledTime, canceledTime,
                quantity, filledPrice, filledQuantity, feeAsset, fees, limitPrice, stopPrice);

        assertEquals(brokerId, order.getBrokerId());
        assertEquals(actionBuy, order.getAction());
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
        assertEquals(stopPrice, order.getStopPrice());
    }

    @Test
    void testConstructorInMemory() {
        BigDecimal quantity = BigDecimal.valueOf(2.0);
        BigDecimal limitPrice = BigDecimal.valueOf(2500);
        BigDecimal stopPrice = BigDecimal.valueOf(2400);

        OrderStopLimit order = new OrderStopLimit(actionBuy, tradingPair, quantity, limitPrice, stopPrice);

        assertEquals(actionBuy, order.getAction());
        assertEquals(tradingPair, order.getTradingPair());
        assertEquals(quantity, order.getQuantity());
        assertEquals(limitPrice, order.getLimitPrice());
        assertEquals(stopPrice, order.getStopPrice());
    }

    @Test
    void testValidateOrder_ThrowsExceptionForNullLimitPrice() {
        BigDecimal quantity = BigDecimal.valueOf(1.0);
        OrderStopLimit order = new OrderStopLimit(actionBuy, tradingPair, quantity, null, BigDecimal.valueOf(34000));

        OrderValidationException exception = assertThrows(OrderValidationException.class, () -> order.validateOrder());
        assertEquals("limitPrice cannot be null or zero", exception.getMessage());
    }

    @Test
    void testValidateOrder_ThrowsExceptionForZeroLimitPrice() {
        BigDecimal quantity = BigDecimal.valueOf(1.0);
        OrderStopLimit order = new OrderStopLimit(actionBuy, tradingPair, quantity, BigDecimal.ZERO,
                BigDecimal.valueOf(34000));

        OrderValidationException exception = assertThrows(OrderValidationException.class, () -> order.validateOrder());
        assertEquals("limitPrice cannot be null or zero", exception.getMessage());
    }

    @Test
    void testValidateOrder_ThrowsExceptionForNullStopPrice() {
        BigDecimal quantity = BigDecimal.valueOf(1.0);
        OrderStopLimit order = new OrderStopLimit(actionBuy, tradingPair, quantity, BigDecimal.valueOf(35000), null);

        OrderValidationException exception = assertThrows(OrderValidationException.class, () -> order.validateOrder());
        assertEquals("limitPrice cannot be null or zero", exception.getMessage());
    }

    @Test
    void testValidateOrder_ThrowsExceptionForZeroStopPrice() {
        BigDecimal quantity = BigDecimal.valueOf(1.0);
        OrderStopLimit order = new OrderStopLimit(actionBuy, tradingPair, quantity, BigDecimal.valueOf(35000),
                BigDecimal.ZERO);

        OrderValidationException exception = assertThrows(OrderValidationException.class, () -> order.validateOrder());
        assertEquals("limitPrice cannot be null or zero", exception.getMessage());
    }

    @Test
    void testValidateBuyOrder_ThrowsExceptionWhenStopPriceLessThanOrEqualToLimitPrice() {
        BigDecimal quantity = BigDecimal.valueOf(1.0);
        BigDecimal limitPrice = BigDecimal.valueOf(35000);
        BigDecimal stopPrice = BigDecimal.valueOf(35000);
        OrderStopLimit order = new OrderStopLimit(actionBuy, tradingPair, quantity, limitPrice, stopPrice);

        OrderValidationException exception = assertThrows(OrderValidationException.class, () -> order.validateOrder());
        assertEquals("stopPrice must be greater than limitPrice", exception.getMessage());
    }

    @Test
    void testValidateSellOrder_ThrowsExceptionWhenLimitPriceLessThanOrEqualToStopPrice() {
        BigDecimal quantity = BigDecimal.valueOf(1.0);
        BigDecimal limitPrice = BigDecimal.valueOf(34000);
        BigDecimal stopPrice = BigDecimal.valueOf(35000);
        OrderStopLimit order = new OrderStopLimit(actionSell, tradingPair, quantity, limitPrice, stopPrice);

        OrderValidationException exception = assertThrows(OrderValidationException.class, () -> order.validateOrder());
        assertEquals("limitPrice must be greater than stopPrice", exception.getMessage());
    }

    @Test
    void testValidateOrder_DoesNotThrowExceptionForValidBuyOrder() {
        BigDecimal quantity = BigDecimal.valueOf(1.0);
        BigDecimal limitPrice = BigDecimal.valueOf(35000);
        BigDecimal stopPrice = BigDecimal.valueOf(36000);
        OrderStopLimit order = new OrderStopLimit(actionBuy, tradingPair, quantity, limitPrice, stopPrice);

        assertDoesNotThrow(() -> order.validateOrder());
    }

    @Test
    void testValidateOrder_DoesNotThrowExceptionForValidSellOrder() {
        BigDecimal quantity = BigDecimal.valueOf(1.0);
        BigDecimal limitPrice = BigDecimal.valueOf(36000);
        BigDecimal stopPrice = BigDecimal.valueOf(35000);
        OrderStopLimit order = new OrderStopLimit(actionSell, tradingPair, quantity, limitPrice, stopPrice);

        assertDoesNotThrow(() -> order.validateOrder());
    }
}
