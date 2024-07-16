package com.bat.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import com.bat.domain.exceptions.OrderValidationException;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Class representing a One-Cancels-the-Other (OCO) order in the trading system.
 * <p>
 * The {@code OrderOco} class extends the {@link Order} class to include
 * additional
 * attributes for a primary order and a secondary stop-limit order. This class
 * provides
 * constructors to initialize the OCO order either from the database or directly
 * in memory.
 * </p>
 * <p>
 * An OCO order is a pair of orders stipulating that if one order is executed,
 * then the other order
 * is automatically canceled. It is used to mitigate risks and protect profits
 * in trading.
 * </p>
 * <p>
 * There are two constructors:
 * </p>
 * <ul>
 * <li>The first constructor is used to initialize an OCO order when loading
 * from the database.</li>
 * <li>The second constructor creates an OCO order in memory directly, with
 * specified action, trading pair, quantity, primary order, and secondary
 * stop-limit order.</li>
 * </ul>
 * 
 * <p>
 * Example usage:
 * </p>
 * 
 * <pre>
 * {@code
 * OrderStopLimit stopLimitOrder = new OrderStopLimit(action, tradingPair, quantity, limitPrice, stopPrice);
 * OrderLimit limitOrder = new OrderLimit(action, tradingPair, quantity, limitPrice);
 * OrderOco ocoOrderFromDb = new OrderOco(brokerId, action, type, tradingPair, creationTime, openTime, filledTime,
 *         canceledTime, quantity, prices, quantities, feeAsset, fees, stopLimitOrder, limitOrder);
 * OrderOco newOcoOrder = new OrderOco(action, tradingPair, quantity, stopLimitOrder, limitOrder);
 * }
 * </pre>
 * 
 * @author SChoumiloff
 * @since 1.0
 */
@Getter
@Setter
@ToString
public class OrderOco extends Order {

    private OrderStopLimit secondaryOrder;
    private OrderLimit primaryOrder;

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
     * @param secondaryOrder         Secondary stop-limit order.
     * @param primaryOrder           Primary limit order.
     */
    public OrderOco(
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
            OrderLimit primaryOrder,
            OrderStopLimit secondaryOrder) {
        super(brokerId, action, type, tradingPair, orderCreationTimestamp, orderOpenTimestamp, orderFilledTimestamp,
                orderCanceledTimestamp, fees, filledPrice, filledQuantity, feeAsset, fees);

        this.primaryOrder = primaryOrder;
        this.secondaryOrder = secondaryOrder;

        if (this.primaryOrder.getStatus().equals(OrderStatus.CANCELED)
                && this.secondaryOrder.getStatus().equals(OrderStatus.EXECUTED))
            this.setQuantity(this.secondaryOrder.getQuantity());
        if (this.secondaryOrder.getStatus().equals(OrderStatus.CANCELED)
                && this.primaryOrder.getStatus().equals(OrderStatus.EXECUTED))
            this.setQuantity(this.primaryOrder.getQuantity());
        if (this.secondaryOrder.getStatus().equals(OrderStatus.OPEN)
                && this.primaryOrder.getStatus().equals(OrderStatus.OPEN))
            this.setQuantity(null);
        if (this.secondaryOrder.getStatus().equals(OrderStatus.CANCELED)
                && this.primaryOrder.getStatus().equals(OrderStatus.CANCELED))
            this.setQuantity(null);
        this.secondaryOrder = secondaryOrder;
        this.primaryOrder = primaryOrder;
    }

    /**
     * Constructor with specific attributes for simplified creation.
     *
     * @param action         Order action (BUY or SELL).
     * @param tradingPair    Trading pair.
     * @param quantity       Order quantity.
     * @param secondaryOrder Secondary stop-limit order.
     * @param primaryOrder   Primary limit order.
     */
    public OrderOco(
            OrderAction action,
            Pair tradingPair,
            BigDecimal quantity,
            OrderStopLimit secondaryOrder,
            OrderLimit primaryOrder) {
        super(action, tradingPair, quantity);
        this.setQuantity(null);
        this.secondaryOrder = secondaryOrder;
        this.primaryOrder = primaryOrder;
    }

    @Override
    public void validateOrder() throws OrderValidationException {
        if (!this.primaryOrder.getAction().equals(secondaryOrder.getAction()))
            throw new OrderValidationException("Primary and secondary orders must have the same action");
        if (!this.primaryOrder.getTradingPair().equals(secondaryOrder.getTradingPair()))
            throw new OrderValidationException("Primary and secondary orders must have the same trading pair");
        if (this.primaryOrder.getStatus().equals(OrderStatus.OPEN)
                && !this.secondaryOrder.getStatus().equals(OrderStatus.OPEN))
            throw new OrderValidationException("Primary order cannot be open while secondary not");

        if (this.secondaryOrder.getStatus().equals(OrderStatus.OPEN)
                && !this.primaryOrder.getStatus().equals(OrderStatus.OPEN))
            throw new OrderValidationException("Secondary order cannot be open while primary not");
    }

    @Override
    protected void validateBuyOrder() throws OrderValidationException {
        this.primaryOrder.validateBuyOrder();
        this.secondaryOrder.validateBuyOrder();
    }

    @Override
    protected void validateSellOrder() throws OrderValidationException {
        this.primaryOrder.validateSellOrder();
        this.secondaryOrder.validateSellOrder();
    }

}