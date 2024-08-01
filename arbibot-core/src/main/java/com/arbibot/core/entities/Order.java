package com.arbibot.core.entities;

import java.math.BigDecimal;
import java.math.RoundingMode;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The Order class represents a trading order for a specific pair of assets on
 * an exchange.
 * It includes details such as the type of order (buy or sell), the quantity of
 * assets,
 * the price of the pair, and the fees associated with the order.
 * 
 * @see com.arbibot.core.entities.Pair
 * @see com.arbibot.core.entities.Asset
 * @see com.arbibot.core.entities.OrderType
 * @see com.arbibot.core.entities.OrderStatus
 * 
 * @author SChoumiloff
 * @author SebastienGuillemin
 * @since 1.0
 */

@Getter
@ToString
public class Order {
    // TODO : Use the step-size to determine ROUNDING_SCALE ?
    private static final int ROUNDING_SCALE = 8;

    private Pair pair;
    private OrderType type;

    @Setter
    /**
     * Default value = NOT_PASSED when the order is created.
     */
    private OrderStatus status;

    @Setter
    private BigDecimal fees;

    private BigDecimal percentFees;
    private BigDecimal quantity;

    @Setter
    private BigDecimal executedQuantity;

    /**
     * 
     * By default the order's status is
     * {@link com.arbibot.core.entities.OrderStatus#NOT_PASSED}
     * 
     * @param pair        the pair on which the order is taken.
     * @param type        Buy or Sell.
     * @param quantity    The quantity to buy or to sell. If {@code type} is
     *                    BUY then the quantity is expressed in quote asset,
     *                    otherwise it is expressed in base asset.
     * @param percentFees percentage of the the wuantoty taken as fees.
     */
    public Order(Pair pair, OrderType type, BigDecimal quantity, BigDecimal percentFees) {
        this.pair = pair;
        this.type = type;
        this.status = OrderStatus.NOT_PASSED;
        this.percentFees = percentFees;
        this.quantity = quantity;

        this.computeFees();
        this.computeExecuted();
    }

    /**
     * 
     * @param pair        the pair on which the order is taken.
     * @param type        Buy or Sell.
     * @param quantity    The quantity to buy or to sell. If {@code type} is
     *                    BUY then the quantity is expressed in quote asset,
     *                    otherwise it is expressed in base asset.
     * @param percentFees percentage of the the wuantoty taken as fees.
     * @param status      the order status.
     */
    public Order(Pair pair, OrderType type, BigDecimal quantity, BigDecimal percentFees, OrderStatus status) {
        this(pair, type, quantity, percentFees);
        this.status = status;
    }

    /**
     * Computes the fees for base asset (if buy) or quote asset (if sell).
     * 
     */
    private void computeFees() {
        switch (this.type) {
            case BUY:
                this.fees = this.quantity.divide(this.pair.getPrice(), ROUNDING_SCALE, RoundingMode.HALF_UP).multiply(
                        this.percentFees.divide(BigDecimal.valueOf(100.0), ROUNDING_SCALE, RoundingMode.HALF_UP));
                break;

            case SELL:
                this.fees = this.quantity.multiply(this.pair.getPrice()).multiply(
                        this.percentFees.divide(BigDecimal.valueOf(100.0), ROUNDING_SCALE, RoundingMode.HALF_UP));
                break;
        }
    }

    /**
     * Computes the net executed quantity for both quote and base asset
     */
    private void computeExecuted() {
        switch (this.type) {
            case BUY:
                this.executedQuantity = this.quantity.divide(this.pair.getPrice(), ROUNDING_SCALE, RoundingMode.HALF_UP)
                        .subtract(this.fees);
                break;

            case SELL:
                this.executedQuantity = this.quantity.multiply(this.pair.getPrice()).subtract(this.fees);
                break;
        }
    }
}