package com.bat.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

import com.bat.domain.exceptions.PairException;

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
 * Pair btcUsd = Pair.create(btc, usd);
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

    /**
     * Constructs a {@code Pair} with the specified base asset and quote asset.
     * The {@code id} is auto-generated to ensure uniqueness.
     *
     * @param baseAsset  the primary asset in the trading pair
     * @param quoteAsset the asset in which the value of the base asset is quoted
     */
    private Pair(Asset baseAsset, Asset quoteAsset) {
        if (baseAsset.getName().equalsIgnoreCase(quoteAsset.getName())) {
            throw new PairException("Base asset and quote asset must be differents.");
        }
        this.baseAsset = baseAsset;
        this.quoteAsset = quoteAsset;

    }

    /**
     * Static factory method to create a {@code Pair} instance.
     * This method ensures the {@code id} is auto-generated internally.
     *
     * @param baseAsset  the base asset
     * @param quoteAsset the quote asset
     * @return a new {@code Pair} instance with a unique ID and specified assets
     */
    public static Pair create(Asset baseAsset, Asset quoteAsset) {
        return new Pair(baseAsset, quoteAsset);
    }
}
