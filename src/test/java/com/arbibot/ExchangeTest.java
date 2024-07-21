package com.arbibot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.arbibot.entities.Asset;
import com.arbibot.entities.Exchange;
import com.arbibot.entities.ExchangeType;
import com.arbibot.entities.Pair;
import com.arbibot.exceptions.ExchangeException;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class ExchangeTest {

    private Asset btc;
    private Asset eth;
    private Asset bnb;
    private Asset usdt;
    private Asset sol;

    private Pair btcusdt;
    private Pair ethusdt;
    private Pair bnbusdt;
    private Pair solusdt;
    private Pair btcbnb;
    private Pair solbnb;
    private Pair ethbnb;
    private Pair solbtc;
    private Pair ethbtc;

    private List<Pair> cexPairs;
    private List<Pair> dexPairs;

    @BeforeEach
    public void setup() {
        btc = Asset.create("BTC");
        eth = Asset.create("ETH");
        bnb = Asset.create("BNB");
        usdt = Asset.create("USDT");
        sol = Asset.create("SOL");

        btcusdt = Pair.create(btc, usdt);
        ethusdt = Pair.create(eth, usdt);
        bnbusdt = Pair.create(bnb, usdt);
        solusdt = Pair.create(sol, usdt);

        btcbnb = Pair.create(btc, bnb);
        solbnb = Pair.create(sol, bnb);
        ethbnb = Pair.create(eth, bnb);

        solbtc = Pair.create(sol, btc);
        ethbtc = Pair.create(eth, btc);

        cexPairs = Arrays.asList(btcusdt, ethusdt, bnbusdt, solusdt);
        dexPairs = Arrays.asList(solusdt, ethusdt, bnbusdt, btcbnb, solbnb, ethbnb, solbtc, ethbtc);
    }

    @Test
    public void testExchangeType() {
        Exchange cexExchange = new Exchange("Binance", "https://binance.com", cexPairs, BigDecimal.valueOf(0.1), bnb,
                ExchangeType.CEX);
        Exchange dexExchange = new Exchange("Uniswap", "https://uniswap.org", dexPairs, BigDecimal.valueOf(0.3), eth,
                ExchangeType.DEX);

        assertEquals(ExchangeType.CEX, cexExchange.getExchangeType());
        assertEquals(ExchangeType.DEX, dexExchange.getExchangeType());
    }

    @Test
    public void testValideExchange() {
        Exchange cexExchange = new Exchange("Binance", "https://binance.com", cexPairs, BigDecimal.valueOf(0.1), bnb,
                ExchangeType.CEX);
        assertDoesNotThrow(() -> cexExchange.validateExchange());
    }

    @Test
    public void testExchangeMustThrowException() {
        List<Pair> cexPairsError = Arrays.asList(btcusdt, ethusdt, bnbusdt, solusdt, solusdt);
        Exchange cexExchange = new Exchange("Binance", "https://binance.com", cexPairsError, BigDecimal.valueOf(0.1),
                bnb,
                ExchangeType.CEX);
        assertThrows(ExchangeException.class, () -> cexExchange.validateExchange());
    }
}
