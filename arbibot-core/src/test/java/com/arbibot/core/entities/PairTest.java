package com.arbibot.core.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.arbibot.core.exceptions.entities.pair.PairNullAssetException;
import com.arbibot.core.exceptions.entities.pair.PairSameAssetException;

public class PairTest {

    @Test
    public void testCreatePair() {
        Asset bitcoin = new Asset("btc");
        Asset usd = new Asset("usdt");

        Pair btcUsdPair = new Pair(bitcoin, usd);

        assertNotNull(btcUsdPair);
        assertNotNull(btcUsdPair.getId());
        assertEquals(bitcoin, btcUsdPair.getBaseAsset());
        assertEquals(usd, btcUsdPair.getQuoteAsset());
    }

    @Test
    public void testPairIdUniqueness() {
        Asset bitcoin = new Asset("btc");
        Asset usd = new Asset("usdt");

        Pair btcUsdPair1 = new Pair(bitcoin, usd);
        Pair btcUsdPair2 = new Pair(bitcoin, usd);

        assertNotNull(btcUsdPair1.getId());
        assertNotNull(btcUsdPair2.getId());
        assertNotEquals(btcUsdPair1.getId(), btcUsdPair2.getId());
    }

    @Test
    public void testPairBaseAndQuoteAssets() {
        Asset bitcoin = new Asset("btc");
        Asset usd = new Asset("usdt");

        Pair btcUsdPair = new Pair(bitcoin, usd);

        assertEquals("btc", btcUsdPair.getBaseAsset().getName());
        assertEquals("usdt", btcUsdPair.getQuoteAsset().getName());
    }

    @Test
    public void testPairSameBaseAndQuoteAssets() {
        Asset bitcoin1 = new Asset("btc");
        Asset bitcoin2 = new Asset("btc");

        Exception exception = assertThrows(PairSameAssetException.class, () -> {
            new Pair(bitcoin1, bitcoin2);
        });
        assertEquals("The two assets (" + "btc" + " and " + "btc"
                + " ) used to create a pair must be different.", exception.getMessage());
    }

    @Test
    public void testBaseAssetNull() {
        Asset nullAsset = null;
        Asset bitcoin2 = new Asset("btc");

        Exception exception = assertThrows(PairNullAssetException.class, () -> new Pair(nullAsset, bitcoin2));
        assertEquals("Base asset cannot be null.", exception.getMessage());
    }

    @Test
    public void testQuoteAssetNull() {
        Asset bitcoin1 = new Asset("btc");
        Asset nullAsset = null;

        Exception exception = assertThrows(PairNullAssetException.class, () -> new Pair(bitcoin1, nullAsset));
        assertEquals("Quote asset cannot be null.", exception.getMessage());
    }
}