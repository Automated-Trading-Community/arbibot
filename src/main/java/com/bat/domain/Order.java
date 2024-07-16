package com.bat.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.bat.domain.exceptions.OrderMisMatchException;
import com.bat.domain.exceptions.OrderValidationException;

/**
 * Abstract class representing an order in the trading system.
 * <p>
 * The {@code Order} class serves as a base class for different types of orders
 * and provides common attributes and methods to manage the state and validation
 * of an order.
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
 * Order orderFromDb = new Order(brokerId, action, type, tradingPair, creationTime, openTime, filledTime, canceledTime,
 *         quantity, prices, quantities, feeAsset, fees);
 * Order newOrder = new Order(action, tradingPair, quantity);
 * }
 * </pre>
 * 
 * @author SChoumiloff
 * @since 1.0
 */
@Getter
@Setter
@ToString
abstract public class Order {

    private final UUID id = UUID.randomUUID();

    private String brokerId;
    private OrderAction action;
    private OrderStatus status;
    private OrderType type;
    private Pair tradingPair;
    private Instant orderCreationTimestamp;
    private Instant orderOpenTimestamp;
    private Instant orderFilledTimestamp;
    private Instant orderCanceledTimestamp;
    private BigDecimal quantity;
    private List<BigDecimal> filledPrice;
    private List<BigDecimal> filledQuantity;
    private Asset feeAsset;
    private BigDecimal fees;

    /**
     * Constructor to initialize an order from external infra.
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
     */
    public Order(
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
            BigDecimal fees) {
        this.brokerId = brokerId;
        this.action = action;
        this.type = type;
        this.tradingPair = tradingPair;
        this.orderCreationTimestamp = orderCreationTimestamp;
        this.orderOpenTimestamp = orderOpenTimestamp;
        this.orderFilledTimestamp = orderFilledTimestamp;
        this.orderCanceledTimestamp = orderCanceledTimestamp;
        this.quantity = quantity;
        this.filledPrice = filledPrice;
        this.filledQuantity = filledQuantity;
        this.feeAsset = feeAsset;
        this.fees = fees;
    }

    /**
     * Constructor to create a new order in memory with default values.
     *
     * @param action      the action of the order (buy/sell)
     * @param tradingPair the trading pair involved in the order
     * @param quantity    the quantity of the asset to be traded
     */
    public Order(
            OrderAction action,
            Pair tradingPair,
            BigDecimal quantity) {
        this.type = OrderType.MARKET;
        this.action = action;
        this.status = OrderStatus.OPEN;
        this.orderCreationTimestamp = Instant.now();
        this.quantity = quantity;
        this.filledPrice = new ArrayList<>();
        this.filledQuantity = new ArrayList<>();
        this.tradingPair = tradingPair;
    }

    /**
     * Calculates the total filled quantity of the order.
     *
     * @return the total filled quantity as a BigDecimal
     * @throws OrderMisMatchException if the order is still open or if there is a
     *                                mismatch between the filled quantity and the
     *                                base quantity
     */
    protected BigDecimal getTotalFilledQuantity() throws OrderMisMatchException {
        if (this.status == OrderStatus.OPEN) {
            throw new OrderMisMatchException("Order is not filled yet");
        } else {
            if (this.filledQuantity != null && this.filledQuantity.size() > 0) {
                return this.filledQuantity.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
            } else {
                throw new OrderMisMatchException("Mismatch between the filled quantity and based quantity");
            }
        }
    }

    /**
     * Validates the order based on specific rules.
     * 
     * @throws OrderValidationException if the order is invalid
     */
    abstract public void validateOrder() throws OrderValidationException;

    /**
     * Validates the order when the action is to buy.
     * 
     * @throws OrderValidationException if the buy order is invalid
     */
    abstract protected void validateBuyOrder() throws OrderValidationException;

    /**
     * Validates the order when the action is to sell.
     * 
     * @throws OrderValidationException if the sell order is invalid
     */
    abstract protected void validateSellOrder() throws OrderValidationException;
}