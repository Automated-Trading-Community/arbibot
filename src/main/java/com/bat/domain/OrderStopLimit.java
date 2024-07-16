package com.bat.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import com.bat.domain.exceptions.OrderValidationException;

import lombok.Getter;
import lombok.Setter;

/**
 * Class representing a stop-limit order in the trading system.
 * <p>
 * The {@code OrderStopLimit} class extends the {@link Order} class to include
 * additional
 * attributes for the stop price and limit price. This class provides
 * constructors to initialize
 * the order either from the database or directly in memory.
 * </p>
 * <p>
 * A stop-limit order combines the features of a stop order and a limit order.
 * When the stop price
 * is reached, the order becomes a limit order at the specified limit price.
 * </p>
 * <p>
 * There are two constructors:
 * </p>
 * <ul>
 * <li>The first constructor is used to initialize an order when loading from
 * the database.</li>
 * <li>The second constructor creates an order in memory directly, with
 * specified action, trading pair, quantity, stop price, and limit price.</li>
 * </ul>
 * 
 * <p>
 * Example usage:
 * </p>
 * 
 * <pre>
 * {@code
 * OrderStopLimit orderFromDb = new OrderStopLimit(brokerId, action, type, tradingPair, creationTime, openTime,
 *         filledTime, canceledTime, quantity, prices, quantities, feeAsset, fees, limitPrice, stopPrice);
 * OrderStopLimit newOrder = new OrderStopLimit(action, tradingPair, quantity, limitPrice, stopPrice);
 * }
 * </pre>
 * 
 * @author SChoumiloff
 * @since 1.0
 */
@Getter
@Setter
public class OrderStopLimit extends Order {

    private BigDecimal limitPrice;
    private BigDecimal stopPrice;

    /**
     * Constructor with all attributes except for the UUID identifier.
     *
     * @param brokerId               Broker identifier.
     * @param action                 Order action (BUY or SELL).
     * @param type                   Order type.
     * @param tradingPair            Trading pair.
     * @param orderCreationTimestamp Timestamp when the order was created.
     * @param orderOpenTimestamp     Timestamp when the order was opened.
     * @param orderFilledTimestamp   Timestamp when the order was filled.
     * @param orderCanceledTimestamp Timestamp when the order was canceled.
     * @param quantity               Order quantity.
     * @param filledPrice            List of filled prices.
     * @param filledQuantity         List of filled quantities.
     * @param feeAsset               Asset used for fees.
     * @param fees                   Amount of fees.
     * @param limitPrice             Limit price of the order.
     * @param stopPrice              Stop price of the order.
     */
    public OrderStopLimit(
            String brokerId,
            OrderAction action,
            OrderType type,
            Pair tradingPair,
            Instant orderCreationTimestamp,
            Instant orderOpenTimestamp,
            Instant orderFilledTimestamp,
            Instant orderCanceledTimestamp,
            BigDecimal quantity,
            List<BigDecimal> filledPrice,
            List<BigDecimal> filledQuantity,
            Asset feeAsset,
            BigDecimal fees,
            BigDecimal limitPrice,
            BigDecimal stopPrice) {
        super(brokerId, action, type, tradingPair, orderCreationTimestamp, orderOpenTimestamp, orderFilledTimestamp,
                orderCanceledTimestamp, quantity, filledPrice, filledQuantity, feeAsset, fees);
        this.limitPrice = limitPrice;
        this.stopPrice = stopPrice;
    }

    /**
     * Constructor with specific attributes for simplified creation.
     *
     * @param action      Order action (BUY or SELL).
     * @param tradingPair Trading pair.
     * @param quantity    Order quantity.
     * @param limitPrice  Limit price of the order.
     * @param stopPrice   Stop price of the order.
     */
    public OrderStopLimit(
            OrderAction action,
            Pair tradingPair,
            BigDecimal quantity,
            BigDecimal limitPrice,
            BigDecimal stopPrice) {
        super(action, tradingPair, quantity);
        this.limitPrice = limitPrice;
        this.stopPrice = stopPrice;
    }

    @Override
    public void validateOrder() throws OrderValidationException {
        if (this.limitPrice == null || this.limitPrice.compareTo(BigDecimal.ZERO) <= 0)
            throw new OrderValidationException("limitPrice cannot be null or zero");
        if (this.stopPrice == null || this.stopPrice.compareTo(BigDecimal.ZERO) <= 0)
            throw new OrderValidationException("limitPrice cannot be null or zero");
        if (this.getAction().equals(OrderAction.BUY))
            this.validateBuyOrder();
        if (this.getAction().equals(OrderAction.SELL))
            this.validateSellOrder();
    }

    @Override
    protected void validateBuyOrder() throws OrderValidationException {
        if (this.stopPrice.compareTo(this.limitPrice) <= 0)
            throw new OrderValidationException("stopPrice must be greater than limitPrice");
    }

    @Override
    protected void validateSellOrder() throws OrderValidationException {
        if (this.limitPrice.compareTo(this.stopPrice) <= 0)
            throw new OrderValidationException("limitPrice must be greater than stopPrice");
    }

}