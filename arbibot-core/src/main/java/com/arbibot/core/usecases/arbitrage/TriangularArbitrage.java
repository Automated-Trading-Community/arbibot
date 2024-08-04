package com.arbibot.core.usecases.arbitrage;

import java.math.BigDecimal;

import com.arbibot.core.entities.Exchange;
import com.arbibot.core.entities.Order;
import com.arbibot.core.entities.OrderType;
import com.arbibot.core.entities.Pair;
import com.arbibot.core.exceptions.TriangularArbitragingException;
import com.arbibot.core.exceptions.entities.ports.output.ExchangePairPriceException;
import com.arbibot.core.ports.input.ForTriangularArbitraging;
import com.arbibot.core.ports.output.ForExchangeCommunication;

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
 * @see com.arbibot.core.ports.output.ForExchangeCommunication
 * @see com.arbibot.core.ports.input.ForTriangularArbitraging
 * 
 * @author SGuillemin
 * @author SChoumiloff
 * @since 1.0
 */
public class TriangularArbitrage implements ForTriangularArbitraging {

    private ForExchangeCommunication forExchangeCommunication;

    /**
     * 
     * @param forExchangeDataRecovery Dependancy required to retrieve information
     *                                from exchnages.
     */
    public TriangularArbitrage(ForExchangeCommunication forExchangeDataRecovery) {
        this.forExchangeCommunication = forExchangeDataRecovery;
    }

    @Override
    /**
     * @inheritDoc
     */
    public Order[] performTriangualarArbitrage(Pair pair1, Pair pair2, Pair pair3, Exchange exchange,
            BigDecimal quantity)
            throws TriangularArbitragingException {

        if (this.validateTriangle(pair1, pair2, pair3)) {
            try {
                // Retrieve pairs price.
                this.forExchangeCommunication.getPriceForPair(pair1);
                this.forExchangeCommunication.getPriceForPair(pair2);
                this.forExchangeCommunication.getPriceForPair(pair3);
            } catch (ExchangePairPriceException e) {
                e.printStackTrace();
                return null;
            }

            // Check is the price are not null
            if (pair1.getPrice() == null)
                throw new TriangularArbitragingException("Price of pair " + pair1 + " is null.");
            if (pair2.getPrice() == null)
                throw new TriangularArbitragingException("Price of pair " + pair2 + " is null.");
            if (pair3.getPrice() == null)
                throw new TriangularArbitragingException("Price of pair " + pair3 + " is null.");

            // Comptute implied rate
            BigDecimal impliedRate = this.computeImpliedRate(pair2.getPrice(), pair3.getPrice());

            // If implied rate is greater than pair 1 price then continue.
            if (impliedRate.compareTo(pair1.getPrice()) > 0) {
                // Create orders
                Order[] orders = this.createOrders(pair1, pair2, pair3, exchange, quantity);

                // Compute fees
                BigDecimal fees = this.computeFeesForBuyCurrency(orders);

                // If (implied rate - fees) > pair 1 price then continue
                if (impliedRate.subtract(fees).compareTo(pair1.getPrice()) > 0) {
                    this.passOrders(orders);

                    // BigDecimal variation = this.computeVaritation(pair1.getPrice(), impliedRate);
                    // TODO : Ajouter une condition supplémentaire en mode si variation sup a 0.5 %
                    // alors
                    // go
                }

                return orders;
            }
            return null;
        } else
            throw new TriangularArbitragingException("Triangle or asset buy is not valid");
    }

    /**
     * Validates if three pairs form a valid triangular arbitrage opportunity.
     *
     * @param pair1 the first pair of the triangle
     * @param pair2 the second pair of the triangle
     * @param pair3 the third pair of the triangle
     * @return true if the pairs form a valid triangle, false otherwise
     */
    private boolean validateTriangle(Pair pair1, Pair pair2, Pair pair3) {
        return pair1.getQuoteAsset().equals(pair3.getQuoteAsset())
                && pair1.getBaseAsset().equals(pair2.getBaseAsset())
                && pair2.getQuoteAsset().equals(pair3.getBaseAsset());
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
     * @param pair1    the first pair
     * @param pair2    the second pair
     * @param pair3    the third pair
     * @param exchange the exchange on which the orders are placed
     * @param quantity the quantity to be traded in the first order
     * @return an array of three orders forming the triangular arbitrage
     */
    private Order[] createOrders(Pair pair1, Pair pair2, Pair pair3, Exchange exchange, BigDecimal quantity) {
        Order[] orders = new Order[3];
        orders[0] = new Order(pair1, OrderType.BUY, quantity, exchange.getFees());
        orders[1] = new Order(pair2, OrderType.SELL, orders[0].getExecutedQuantity(),
                exchange.getFees());
        orders[2] = new Order(pair3, OrderType.SELL, orders[1].getExecutedQuantity(),
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
        for (Order order : orders)
            // TODO : envoyer/ne pas envoyer les ordres en fonction des codes de retours des
            // ordres précédents
            this.forExchangeCommunication.passOrder(order);
    }

    // private BigDecimal computeVaritation(BigDecimal initPrice, BigDecimal
    // finalPrice) {
    // return
    // ((finalPrice.subtract(initPrice)).divide(initPrice)).multiply(BigDecimal.valueOf(100));
    // }
}