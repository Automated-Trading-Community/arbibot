package com.arbibot.usecases.arbitrage;

import java.math.BigDecimal;

import com.arbibot.entities.Asset;
import com.arbibot.entities.Exchange;
import com.arbibot.entities.Order;
import com.arbibot.entities.OrderType;
import com.arbibot.entities.Pair;
import com.arbibot.ports.input.ForTriangularArbitraging;
import com.arbibot.ports.output.ForExchangeDataRecovery;
import com.arbibot.usecases.arbitrage.exceptions.TriangularArbitragingException;

public class TriangularArbitrage implements ForTriangularArbitraging {

    private ForExchangeDataRecovery forExchangeDataRecovery;
    private Exchange exchange;
    private Asset buyCurrency;

    public TriangularArbitrage(ForExchangeDataRecovery forExchangeDataRecovery, Exchange exchange, Asset buyCurrency) {
        this.forExchangeDataRecovery = forExchangeDataRecovery;
        this.exchange = exchange;
        this.buyCurrency = buyCurrency;
    }

    @Override
    public void performTriangualarArbitrage(Pair p1, Pair p2, Pair p3, Exchange exchange, BigDecimal qttBaseAsset,
            BigDecimal qttQuoteAsset) throws TriangularArbitragingException {
        if (this.validateTriangle(p1, p2, p3)) {
            BigDecimal priceP1 = this.forExchangeDataRecovery.getPriceForPair(p1);
            BigDecimal priceP2 = this.forExchangeDataRecovery.getPriceForPair(p2);
            BigDecimal priceP3 = this.forExchangeDataRecovery.getPriceForPair(p3);
            BigDecimal priceBuyCurrencyP2 = this.forExchangeDataRecovery
                    .getPriceForPair(Pair.create(p2.getBaseAsset(), this.buyCurrency));
            BigDecimal impliedRate = this.computeImpliedRate(priceP2, priceP3);
            if (impliedRate.compareTo(priceP1) < 0) {
                BigDecimal fees = this.computeFeesForBuyCurrency(p1, p2, p3, priceP1, priceP2, priceP3,
                        priceBuyCurrencyP2, qttQuoteAsset, impliedRate, impliedRate);
                if (fees.equals(BigDecimal.ZERO)) {
                    return;
                } else {
                    // TODO passer les ordres au fur et à mesure avec forExchangeDataRecovery
                }
            } else {
                return;
            }
        } else
            throw new TriangularArbitragingException("Triangle or asset buy is not valid");
    }

    private boolean validateTriangle(Pair p1, Pair p2, Pair p3) {
        boolean isTriangular = p1.getQuoteAsset().equals(p3.getQuoteAsset())
                && p1.getBaseAsset().equals(p2.getBaseAsset())
                && p2.getQuoteAsset().equals(p3.getBaseAsset());
        boolean isCurrencyBuy = p1.getQuoteAsset().equals(this.buyCurrency);
        return isCurrencyBuy && isTriangular;
    }

    private BigDecimal computeImpliedRate(BigDecimal priceP2, BigDecimal priceP3) {
        return priceP2.multiply(priceP3);
    }

    private BigDecimal computeFeesForBuyCurrency(Pair p1, Pair p2, Pair p3, BigDecimal priceP1, BigDecimal priceP2,
            BigDecimal priceP3, BigDecimal priceBuyCurrencyP2, BigDecimal qttBuyCurrency, BigDecimal impliedRate,
            BigDecimal realRate) {
        Order orderBuy = new Order(p1, OrderType.BUY, qttBuyCurrency, null, priceP1, this.exchange.getFees());
        BigDecimal fees = orderBuy.getFeesQuoteAsset();

        if (fees.add(impliedRate).compareTo(realRate) > 0) {
            return BigDecimal.ZERO;
        } else {
            Order orderSell1 = new Order(p2, OrderType.SELL, qttBuyCurrency, null,
                    priceP1, this.exchange.getFees());
            fees = fees.add(orderSell1.getFeesQuoteAsset().multiply(priceBuyCurrencyP2));
            if (fees.add(impliedRate).compareTo(realRate) > 0) {
                return BigDecimal.ZERO;
            } else {
                Order orderSell2 = new Order(p3, OrderType.SELL, qttBuyCurrency, null,
                        priceP1, this.exchange.getFees());
                fees = fees.add(orderSell2.getFeesQuoteAsset());
                if (fees.add(impliedRate).compareTo(realRate) > 0) {
                    return BigDecimal.ZERO;
                } else {
                    return fees;
                }
            }
        }
    }
}