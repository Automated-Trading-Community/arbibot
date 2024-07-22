package com.arbibot.entities;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

import com.arbibot.exceptions.PairException;

/**
 * The {@code Pair} class represents a trading pair consisting of two financial
 * assets.
 * A trading pair is used to express the value of one asset relative to another.
 * For example, in the trading pair BTC/USD, BTC is the base asset and USD is
 * the quote asset.
 * 
 * This class includes:
 * - A unique identifier for the trading pair.
 * - A base asset which is the primary asset in the pair.
 * - A quote asset which is the asset in which the value of the base asset is
 * quoted.
 * 
 * Example usage:
 * 
 * <pre>
 * {@code
 * Asset btc = Asset.create("Bitcoin");
 * Asset usd = Asset.create("US Dollar");
 * Pair btcUsd = new Pair(btc, usd);
 * System.out.println("Trading Pair ID: " + btcUsd.getId());
 * System.out.println("Base Asset: " + btcUsd.getBaseAsset().getName());
 * System.out.println("Quote Asset: " + btcUsd.getQuoteAsset().getName());
 * }
 * </pre>
 * 
 * @author SChoumiloff
 * @since 1.0
 * 
 */
@Getter
@Setter
public class Pair {
    private final UUID id = UUID.randomUUID();

    private Asset baseAsset;
    private Asset quoteAsset;
    private BigDecimal price;

    /**
     * Constructs a {@code Pair} with the specified base asset and quote asset.
     * The {@code id} is auto-generated to ensure uniqueness.
     *
     * @param baseAsset  the primary asset in the trading pair
     * @param quoteAsset the asset in which the value of the base asset is quoted
     */
    public Pair(Asset baseAsset, Asset quoteAsset) {
        if (baseAsset.getName().equalsIgnoreCase(quoteAsset.getName())) {
            throw new PairException("Base asset and quote asset must be differents.");
        }
        this.baseAsset = baseAsset;
        this.quoteAsset = quoteAsset;
    }

    /**
     * 
     * @param baseAsset
     * @param quoteAsset
     * @param price
     */
    public Pair(Asset baseAsset, Asset quoteAsset, BigDecimal price) {
        this(baseAsset, quoteAsset);
        this.price = price;
    }

    @Override
    public boolean equals(Object obj) {
        Pair comparedPair = (Pair) obj;
        if (this.baseAsset.equals(comparedPair.getBaseAsset()) && this.quoteAsset.equals(comparedPair.getQuoteAsset()))
            return true;
        return false;
    }

    @Override
    public String toString() {
        return baseAsset.getName() + "/" + quoteAsset.getName() + this.price != null ? " (" + this.price + ")" : "";
    }
}
