package com.bat.core.domain.entities;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.Instant;

import org.junit.jupiter.api.Test;

import com.bat.core.domain.exceptions.BarException;

public class BarTest {

    @Test
    public void testValidBar() throws BarException {
        Asset btc = Asset.create("BTC");
        Asset usd = Asset.create("USD");
        Pair btcUsdPair = Pair.create(btc, usd);

        Bar validBar = new Bar(
                new BigDecimal("50000"),
                new BigDecimal("51000"),
                new BigDecimal("52000"),
                new BigDecimal("49000"),
                TimeFrame.ONE_HOUR,
                Instant.parse("2023-01-01T10:00:00Z"),
                Instant.parse("2023-01-01T11:00:00Z"),
                btcUsdPair
        );

        validBar.validateBar();  // Should not throw any exception
    }

    @Test
    public void testBarWithInvalidTimes() {
        Asset btc = Asset.create("BTC");
        Asset usd = Asset.create("USD");
        Pair btcUsdPair = Pair.create(btc, usd);

        Bar invalidBar = new Bar(
                new BigDecimal("50000"),
                new BigDecimal("51000"),
                new BigDecimal("52000"),
                new BigDecimal("49000"),
                TimeFrame.ONE_HOUR,
                Instant.parse("2023-01-01T12:00:00Z"),
                Instant.parse("2023-01-01T11:00:00Z"),
                btcUsdPair
        );

        assertThrows(BarException.class, invalidBar::validateBar, "Open time cannot be after close time");
    }

    @Test
    public void testBarWithInvalidPrices() {
        Asset btc = Asset.create("BTC");
        Asset usd = Asset.create("USD");
        Pair btcUsdPair = Pair.create(btc, usd);

        Bar invalidBar = new Bar(
                new BigDecimal("50000"),
                new BigDecimal("51000"),
                new BigDecimal("48000"),
                new BigDecimal("49000"),
                TimeFrame.ONE_HOUR,
                Instant.parse("2023-01-01T10:00:00Z"),
                Instant.parse("2023-01-01T11:00:00Z"),
                btcUsdPair
        );

        assertThrows(BarException.class, invalidBar::validateBar, "Low cannot be greater than high");
    }

    @Test
    public void testBarWithExceededTimeFrame() {
        Asset btc = Asset.create("BTC");
        Asset usd = Asset.create("USD");
        Pair btcUsdPair = Pair.create(btc, usd);

        Bar invalidBar = new Bar(
                new BigDecimal("50000"),
                new BigDecimal("51000"),
                new BigDecimal("52000"),
                new BigDecimal("49000"),
                TimeFrame.ONE_HOUR,
                Instant.parse("2023-01-01T10:00:00Z"),
                Instant.parse("2023-01-01T12:00:00Z"),
                btcUsdPair
        );

        assertThrows(BarException.class, invalidBar::validateBar, "Time duration cannot exceed time frame (" + invalidBar.getTimeFrame().getMinutes() + " minutes)");
    }
}