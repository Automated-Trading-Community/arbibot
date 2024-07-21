package com.arbibot.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.arbibot.exceptions.PairException;

public class PairTest {

    @Test
    public void testCreatePair() {
        Asset bitcoin = Asset.create("btc");
        Asset usd = Asset.create("usdt");

        Pair btcUsdPair = Pair.create(bitcoin, usd);

        assertNotNull(btcUsdPair);
        assertNotNull(btcUsdPair.getId());
        assertEquals(bitcoin, btcUsdPair.getBaseAsset());
        assertEquals(usd, btcUsdPair.getQuoteAsset());
    }

    @Test
    public void testPairIdUniqueness() {
        Asset bitcoin = Asset.create("btc");
        Asset usd = Asset.create("usdt");

        Pair btcUsdPair1 = Pair.create(bitcoin, usd);
        Pair btcUsdPair2 = Pair.create(bitcoin, usd);

        assertNotNull(btcUsdPair1.getId());
        assertNotNull(btcUsdPair2.getId());
        assertNotEquals(btcUsdPair1.getId(), btcUsdPair2.getId());
    }

    @Test
    public void testPairBaseAndQuoteAssets() {
        Asset bitcoin = Asset.create("btc");
        Asset usd = Asset.create("usdt");

        Pair btcUsdPair = Pair.create(bitcoin, usd);

        assertEquals("btc", btcUsdPair.getBaseAsset().getName());
        assertEquals("usdt", btcUsdPair.getQuoteAsset().getName());
    }

    @Test
    public void testPairSameBaseAndQuoteAssets() {
        Asset bitcoin1 = Asset.create("btc");
        Asset bitcoin2 = Asset.create("btc");

        Exception exception = assertThrows(PairException.class, () -> {
            Pair.create(bitcoin1, bitcoin2);
        });
        assertEquals("Base asset and quote asset must be differents.", exception.getMessage());
    }
}
