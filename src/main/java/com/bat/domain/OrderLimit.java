package com.bat.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import com.bat.domain.exceptions.OrderValidationException;

import lombok.Getter;
import lombok.Setter;

/**
 * Class representing a limit order in the trading system.
 * <p>
 * The {@code OrderLimit} class extends the {@link Order} class to include an
 * additional
 * attribute for the limit price. This class provides constructors to initialize
 * the order either from the database or directly in memory.
 * </p>
 * <p>
 * There are two constructors:
 * </p>
 * <ul>
 * <li>The first constructor is used to initialize an order when loading from
 * the database.</li>
 * <li>The second constructor creates an order in memory directly, with a
 * default type of market and status of open.</li>
 * </ul>
 * 
 * <p>
 * Example usage:
 * </p>
 * 
 * <pre>
 * {@code
 * OrderLimit orderFromDb = new OrderLimit(brokerId, action, type, tradingPair, creationTime, openTime, filledTime,
 *         canceledTime, quantity, prices, quantities, feeAsset, fees, limitPrice);
 * OrderLimit newOrder = new OrderLimit(action, tradingPair, quantity, limitPrice);
 * }
 * </pre>
 * 
 * @author SChoumiloff
 * @since 1.0
 */
@Getter
@Setter
public class OrderLimit extends Order {

    private BigDecimal limitPrice;

    /**
     * Constructor to initialize a limit order from the database.
     *
     * @param brokerId               the identifier of the broker
     * @param action                 the action of the order (buy/sell)
     * @param type                   the type of the order
     * @param tradingPair            the trading pair involved in the order
     * @param orderCreationTimestamp the timestamp when the order was created
     * @param orderOpenTimestamp     the timestamp when the order was opened
     * @param orderFilledTimestamp   the timestamp when the order was filled
     * @param orderCanceledTimestamp the timestamp when the order was canceled
     * @param quantity               the quantity of the asset to be traded
     * @param filledPrice            the list of prices at which portions of the
     *                               order were filled
     * @param filledQuantity         the list of quantities that were filled at
     *                               corresponding prices
     * @param feeAsset               the asset used to pay fees
     * @param fees                   the total fees paid
     * @param limitPrice             the limit price for the order
     */
    public OrderLimit(
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
            BigDecimal limitPrice) {
        super(brokerId, action, type, tradingPair, orderCreationTimestamp, orderOpenTimestamp, orderFilledTimestamp,
                orderCanceledTimestamp, quantity, filledPrice, filledQuantity, feeAsset, fees);
        this.limitPrice = limitPrice;
    }

    /**
     * Constructor to create a new limit order in memory with default values.
     *
     * @param action      the action of the order (buy/sell)
     * @param tradingPair the trading pair involved in the order
     * @param quantity    the quantity of the asset to be traded
     * @param limitPrice  the limit price for the order
     */
    public OrderLimit(
            OrderAction action,
            Pair tradingPair,
            BigDecimal quantity,
            BigDecimal limitPrice) {
        super(action, tradingPair, quantity);
        this.limitPrice = limitPrice;
    }

    /**
     * Validates the limit order based on specific rules.
     * 
     * @throws OrderValidationException if the limit price is null or zero
     */
    @Override
    public void validateOrder() throws OrderValidationException {
        if (this.limitPrice == null || this.limitPrice.compareTo(BigDecimal.ZERO) <= 0)
            throw new OrderValidationException("limitPrice cannot be null or zero");
    }

    /**
     * Validates the limit order when the action is to buy.
     * 
     * @throws OrderValidationException if the buy order is invalid
     */
    @Override
    protected void validateBuyOrder() throws OrderValidationException {
    }

    /**
     * Validates the limit order when the action is to sell.
     * 
     * @throws OrderValidationException if the sell order is invalid
     */
    @Override
    protected void validateSellOrder() throws OrderValidationException {
    }
}
