package com.arbibot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.arbibot.entities.Exchange;
import com.arbibot.entities.ExchangeType;

public class ExchangeTest {
    @Test
    public void testExchangeType() {
        Exchange cexExchange = new Exchange("Binance", "https://binance.com", BigDecimal.valueOf(0.1),
                ExchangeType.CEX);
        Exchange dexExchange = new Exchange("Uniswap", "https://uniswap.org", BigDecimal.valueOf(0.3),
                ExchangeType.DEX);

        assertEquals(ExchangeType.CEX, cexExchange.getExchangeType());
        assertEquals(ExchangeType.DEX, dexExchange.getExchangeType());
    }
}
