package com.arbibot.usecases.arbitrage;

import java.math.BigDecimal;

import com.arbibot.entities.Exchange;
import com.arbibot.entities.Order;
import com.arbibot.entities.OrderType;
import com.arbibot.entities.Pair;
import com.arbibot.entities.Order.Reference;
import com.arbibot.ports.input.ForTriangularArbitraging;
import com.arbibot.ports.output.ForExchangeDataRecovery;
import com.arbibot.usecases.arbitrage.exceptions.TriangularArbitragingException;

public class TriangularArbitrage implements ForTriangularArbitraging {

    private ForExchangeDataRecovery forExchangeDataRecovery;

    public TriangularArbitrage(ForExchangeDataRecovery forExchangeDataRecovery) {
        this.forExchangeDataRecovery = forExchangeDataRecovery;
    }

    @Override
    public void performTriangualarArbitrage(Pair p1, Pair p2, Pair p3, Exchange exchange, BigDecimal quantity)
            throws TriangularArbitragingException {

        if (this.validateTriangle(p1, p2, p3)) {
            this.forExchangeDataRecovery.getPriceForPair(p1, exchange);
            this.forExchangeDataRecovery.getPriceForPair(p2, exchange);
            this.forExchangeDataRecovery.getPriceForPair(p3, exchange);

            assert p1.getPrice() != null : p1.toString() + " price is null";
            assert p2.getPrice() != null : p2.toString() + " price is null";
            assert p3.getPrice() != null : p3.toString() + " price is null";

            BigDecimal impliedRate = this.computeImpliedRate(p2.getPrice(), p3.getPrice());

            if (impliedRate.compareTo(p1.getPrice()) < 0) {
                Order[] orders = this.createOrders(p1, p2, p3, exchange, quantity);
                BigDecimal fees = this.computeFeesForBuyCurrency(orders);

                if (fees.add(impliedRate).compareTo(p1.getPrice()) < 0)
                    this.passOrders(orders);
            }
        } else
            throw new TriangularArbitragingException("Triangle or asset buy is not valid");
    }

    private boolean validateTriangle(Pair p1, Pair p2, Pair p3) {
        return p1.getQuoteAsset().equals(p3.getQuoteAsset())
                && p1.getBaseAsset().equals(p2.getBaseAsset())
                && p2.getQuoteAsset().equals(p3.getBaseAsset());
    }

    private BigDecimal computeImpliedRate(BigDecimal priceP2, BigDecimal priceP3) {
        return priceP2.multiply(priceP3);
    }

    private Order[] createOrders(Pair p1, Pair p2, Pair p3, Exchange exchange, BigDecimal quantity) {
        Order[] orders = new Order[3];
        orders[0] = new Order(p1, OrderType.BUY, quantity, Reference.QUOTE, p1.getPrice(), exchange.getFees());
        orders[1] = new Order(p2, OrderType.SELL, orders[0].getExexutedQuantityBaseAsset(), Reference.BASE,
                p2.getPrice(), exchange.getFees());
        orders[2] = new Order(p3, OrderType.SELL, orders[1].getExexutedQuantityQuoteAsset(), Reference.QUOTE,
                p3.getPrice(), exchange.getFees());

        return orders;
    }

    private BigDecimal computeFeesForBuyCurrency(Order[] orders) {
        return orders[0].getFeesQuoteAsset().add(
                orders[1].getFeesbaseAsset().add(
                        orders[2].getFeesbaseAsset()));
    }

    private void passOrders(Order[] orders) {
        // Pass orders.
    }
}