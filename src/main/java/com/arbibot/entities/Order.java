package com.arbibot.entities;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

/**
 * The Order class represents a trading order for a specific pair of assets on
 * an exchange.
 * It includes details such as the type of order (buy or sell), the quantity of
 * assets,
 * the price of the pair, and the fees associated with the order.
 * 
 * @see com.arbibot.entities.Pair
 * @see com.arbibot.entities.Asset
 * @see com.arbibot.entities.OrderType
 * @see com.arbibot.entities.OrderStatus
 * 
 * @author SGuillemin
 * @author SChoumiloff
 * 
 * @since 1.0
 */
@Getter
public class Order {

    public enum Reference {
        QUOTE,
        BASE
    }

    private Pair pair;
    private OrderType type;

    @Setter
    private OrderStatus status;

    private BigDecimal qttQuoteAsset;
    private BigDecimal qttBaseAsset;
    private Reference reference;
    private BigDecimal currentPairPrice;
    private BigDecimal percentFees;

    @Setter
    private BigDecimal fees;

    @Setter
    private BigDecimal exexutedQuantityBaseAsset;
    @Setter
    private BigDecimal exexutedQuantityQuoteAsset;

    /**
     * 
     * constructor
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
                this.qttBaseAsset = qttQuoteAsset.divide(currentPairPrice);
                break;
            case BASE:
                this.qttBaseAsset = quantity;
                this.qttQuoteAsset = qttBaseAsset.multiply(currentPairPrice);
                break;
        }
        this.computeFees();
        this.computeExecuted();
    }

    /**
     * Computes the fees for base asset (if buy) or quote asset (if sell)
     */
    private void computeFees() {
        switch (this.type) {
            case BUY:
                // Dans le cas d'un ordre d'achat les frais sont payés suivant le baseAsset
                // Exemple j'achete 1 btc sur la paire btc/usdt avec des frais de 0.1% alors je
                // vais payer
                // 1 * 0.001 = 0.001 btc
                this.fees = this.qttBaseAsset.multiply(this.percentFees.divide(BigDecimal.valueOf(100)));
                break;
            case SELL:
                // Dans le cas d'un ordre de vente les frais sont payés suivant le quoteAsset
                // Exemple je vend 1 btc sur la paire btc/usdt avec des frais de 0.1% alors je
                // vais payer priceBTC * 0.001 de frais
                // Si btc/usdt = 40000 je vais payer 40 USDT de frais
                this.fees = this.qttQuoteAsset.multiply(this.percentFees.divide(BigDecimal.valueOf(100)));
                break;
        }
    }

    /**
     * Computes the net executed quantity for both quote and base asset
     */
    private void computeExecuted() {
        switch (this.type) {
            case BUY:
                this.exexutedQuantityBaseAsset = this.qttBaseAsset.subtract(this.fees);
                this.exexutedQuantityQuoteAsset = this.exexutedQuantityBaseAsset.multiply(this.currentPairPrice);
                break;
            case SELL:
                this.exexutedQuantityQuoteAsset = this.qttQuoteAsset.subtract(fees);
                this.exexutedQuantityBaseAsset = this.exexutedQuantityQuoteAsset.divide(currentPairPrice);
                break;
        }
    }
}
