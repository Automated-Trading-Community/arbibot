package com.arbibot.usecases.arbitrage;

import java.math.BigDecimal;

import com.arbibot.entities.Exchange;
import com.arbibot.entities.Order;
import com.arbibot.entities.OrderType;
import com.arbibot.entities.Pair;
import com.arbibot.exceptions.TriangularArbitragingException;
import com.arbibot.ports.input.ForTriangularArbitraging;
import com.arbibot.ports.output.ForExchangeCommunication;

/**
 * The TriangularArbitrage class implements the logic for performing triangular
 * arbitrage on given asset pairs and exchange.
 * <p>
 * Triangular arbitrage involves trading between three different pairs of assets
 * in a cycle to take advantage of discrepancies in their exchange rates. This
 * class validates the triangular relationship, computes implied rates, creates
 * orders, and calculates the associated fees before executing the orders if
 * conditions are favorable.
 * </p>
 * 
 * @see com.arbibot.ports.output.ForExchangeCommunication
 * @see com.arbibot.ports.input.ForTriangularArbitraging
 * 
 * @author SGuillemin
 * @author SChoumiloff
 * @since 1.0
 */
public class TriangularArbitrage implements ForTriangularArbitraging {

    private ForExchangeCommunication forExchangeCommunication;

    /**
     * 
     * @param forExchangeDataRecovery dependancy required to retrieve information
     *                                from exchnages.
     */
    public TriangularArbitrage(ForExchangeCommunication forExchangeDataRecovery) {
        this.forExchangeCommunication = forExchangeDataRecovery;
    }

    @Override
    public void performTriangualarArbitrage(Pair p1, Pair p2, Pair p3, Exchange exchange, BigDecimal quantity)
            throws TriangularArbitragingException {

        if (this.validateTriangle(p1, p2, p3)) {
            this.forExchangeCommunication.getPriceForPair(p1, exchange);
            this.forExchangeCommunication.getPriceForPair(p2, exchange);
            this.forExchangeCommunication.getPriceForPair(p3, exchange);

            assert p1.getPrice() != null : p1.toString() + " price is null";
            assert p2.getPrice() != null : p2.toString() + " price is null";
            assert p3.getPrice() != null : p3.toString() + " price is null";

            BigDecimal impliedRate = this.computeImpliedRate(p2.getPrice(), p3.getPrice());
            if (impliedRate.compareTo(p1.getPrice()) > 0) {
                Order[] orders = this.createOrders(p1, p2, p3, exchange, quantity);
                BigDecimal fees = this.computeFeesForBuyCurrency(orders);
                if (impliedRate.subtract(fees).compareTo(p1.getPrice()) > 0) {
                    this.passOrders(orders);
                    // BigDecimal variation = this.computeVaritation(p1.getPrice(), impliedRate);
                    // Ajouter une condition supplémentaire en mode si variation sup a 0.5 % alors
                    // go
                }
            }
        } else
            throw new TriangularArbitragingException("Triangle or asset buy is not valid");
    }

    /**
     * Validates if three pairs form a valid triangular arbitrage opportunity.
     *
     * @param p1 the first pair of the triangle
     * @param p2 the second pair of the triangle
     * @param p3 the third pair of the triangle
     * @return true if the pairs form a valid triangle, false otherwise
     */
    private boolean validateTriangle(Pair p1, Pair p2, Pair p3) {
        return p1.getQuoteAsset().equals(p3.getQuoteAsset())
                && p1.getBaseAsset().equals(p2.getBaseAsset())
                && p2.getQuoteAsset().equals(p3.getBaseAsset());
    }

    /**
     * Computes the implied rate from two given prices.
     *
     * @param priceP2 the price of the second pair
     * @param priceP3 the price of the third pair
     * @return the implied rate as the product of the two prices
     */
    private BigDecimal computeImpliedRate(BigDecimal priceP2, BigDecimal priceP3) {
        return priceP2.multiply(priceP3);
    }

    /**
     * Creates an array of orders for executing a triangular arbitrage.
     *
     * @param p1       the first pair
     * @param p2       the second pair
     * @param p3       the third pair
     * @param exchange the exchange on which the orders are placed
     * @param quantity the quantity to be traded in the first order
     * @return an array of three orders forming the triangular arbitrage
     */
    private Order[] createOrders(Pair p1, Pair p2, Pair p3, Exchange exchange, BigDecimal quantity) {
        Order[] orders = new Order[3];
        orders[0] = new Order(p1, OrderType.BUY, quantity, exchange.getFees());
        orders[1] = new Order(p2, OrderType.SELL, orders[0].getExecutedQuantity(),
                exchange.getFees());
        orders[2] = new Order(p3, OrderType.SELL, orders[1].getExecutedQuantity(),
                exchange.getFees());

        return orders;
    }

    /**
     * Computes the total fees for buying currency in a triangular arbitrage.
     *
     * @param orders the array of orders executed
     * @return the total fees as a BigDecimal
     */
    private BigDecimal computeFeesForBuyCurrency(Order[] orders) {
        BigDecimal feesOrder1, feesOrder2, feesOrder3;
        feesOrder1 = orders[0].getFees().multiply(orders[0].getPair().getPrice());
        feesOrder2 = orders[1].getFees().multiply(orders[2].getPair().getPrice());
        feesOrder3 = orders[2].getFees();

        return feesOrder1.add(feesOrder2).add(feesOrder3);
    }

    /**
     * Passes an array of orders to the exchange communication layer for execution.
     *
     * @param orders the array of orders to be passed
     */
    private void passOrders(Order[] orders) {
        this.forExchangeCommunication.passOrders(orders);
    }

    // private BigDecimal computeVaritation(BigDecimal initPrice, BigDecimal
    // finalPrice) {
    // return
    // ((finalPrice.subtract(initPrice)).divide(initPrice)).multiply(BigDecimal.valueOf(100));
    // }
}