package com.arbibot.entities;

import java.math.BigDecimal;

import lombok.Getter;

@Getter
public class Order {
    public enum Reference {
        QUOTE,
        BASE
    }

    private Pair pair;
    private OrderType type;
    private BigDecimal qttQuoteAsset;
    private BigDecimal qttBaseAsset;
    private Reference reference;
    private BigDecimal currentPairPrice;
    private BigDecimal feesbaseAsset;
    private BigDecimal feesQuoteAsset;
    private BigDecimal percentFees;
    private BigDecimal exexutedQuantityBaseAsset;
    private BigDecimal exexutedQuantityQuoteAsset;

    // TODO : je suggère qu'on ajoute le statut de l'ordre (SUCCESS, REJECTED etc.)
    // et qu'on ait des accessseurs pour l'état.

    /**
     * 
     * constructore
     * 
     * @param pair
     * @param type
     * @param quantity
     * @param reference
     * @param currentPairPrice
     * @param percentFees
     */
    public Order(Pair pair, OrderType type, BigDecimal quantity, Reference reference,
            BigDecimal currentPairPrice,
            BigDecimal percentFees) {
        this.pair = pair;
        this.type = type;
        this.currentPairPrice = currentPairPrice;
        this.percentFees = percentFees;

        switch (reference) {
            case QUOTE:
                this.qttQuoteAsset = quantity;
                this.qttBaseAsset = qttQuoteAsset.multiply(currentPairPrice);
                break;
            case BASE:
                this.qttBaseAsset = quantity;
                this.qttQuoteAsset = qttBaseAsset.divide(currentPairPrice);
                break;
        }
        this.computeFees();
        this.computeExecuted();
    }

    /**
     * Compute Order fees (in executed quantity and in quote asset)
     */
    private void computeFees() {
        this.feesbaseAsset = this.qttBaseAsset.multiply(this.percentFees.divide(BigDecimal.valueOf(100)));
        this.feesQuoteAsset = this.qttQuoteAsset.multiply(this.percentFees.divide(BigDecimal.valueOf(100)));
    }

    /**
     * Compute executed quantity in both (quote & base asset)
     */
    private void computeExecuted() {
        this.exexutedQuantityBaseAsset = this.qttBaseAsset.subtract(this.feesbaseAsset);
        this.exexutedQuantityQuoteAsset = this.qttQuoteAsset.subtract(this.feesQuoteAsset);
    }

    /**
     * Compute fees in usd
     * 
     * @param valueBaseAssetUsd
     * @return
     */
    public BigDecimal computeFeesUsd(BigDecimal valueBaseAssetUsd) {
        return this.exexutedQuantityBaseAsset.multiply(valueBaseAssetUsd);
    }
}
