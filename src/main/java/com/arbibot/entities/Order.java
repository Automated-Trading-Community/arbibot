package com.arbibot.entities;

import java.math.BigDecimal;

import lombok.Getter;

@Getter
public class Order {

    private Pair pair;
    private OrderType type;
    private BigDecimal qttQuoteAsset;
    private BigDecimal qttBaseAsset;
    private BigDecimal currentPairPrice;
    private BigDecimal feesbaseAsset;
    private BigDecimal feesQuoteAsset;
    private BigDecimal percentFees;
    private BigDecimal exexutedQuantityBaseAsset;
    private BigDecimal exexutedQuantityQuoteAsset;

    public Order(Pair pair, OrderType type, BigDecimal qttQuoteAsset, BigDecimal qttBaseAsset,
            BigDecimal currentPairPrice,
            BigDecimal percentFees)
            throws IllegalArgumentException {
        this.pair = pair;
        this.type = type;
        this.currentPairPrice = currentPairPrice;
        this.percentFees = percentFees;
        if (qttQuoteAsset != null) {
            this.qttQuoteAsset = qttQuoteAsset;
            this.qttBaseAsset = qttQuoteAsset.multiply(currentPairPrice);
        } else if (qttBaseAsset != null) {
            this.qttBaseAsset = qttBaseAsset;
            this.qttQuoteAsset = qttBaseAsset.divide(currentPairPrice);
        } else
            throw new IllegalArgumentException("Both qttBaseAsset and qttQuoteAsset cannot be null");
        this.computeFees();
    }

    private void computeFees() {
        this.exexutedQuantityBaseAsset = this.qttBaseAsset.multiply(this.percentFees.divide(BigDecimal.valueOf(100)));
        this.exexutedQuantityQuoteAsset = this.qttQuoteAsset.multiply(this.percentFees.divide(BigDecimal.valueOf(100)));
    }
}
