package com.arbibot.core.entities;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

/**
 * The {@code Exchange} class represents the exchange entity.
 * It can be :
 * <ul>
 * <li>A CEX (Centralized Exchange) : a platform where currencies can be bought,
 * sold and traded throw an intermediary (Binance, Bybit, Kucoin, ...)</li>
 * <li>A DEX (Decentralized Exchange) : a platform where currencies can be trade
 * without any authorities (sushiswap, aave, uniswap ...)</li>
 * </ul>
 * Example usage:
 * 
 * <pre>
 * {@code
 * Asset btc = Asset.create("BTC");
 * Asset usd = Asset.create("USDT");
 * Asset eth = Asset.create("ETH");
 * Exchange binance = new Exchange();
 * }
 * </pre>
 * 
 * @author SChoumiloff
 * @author SebastienGuillemin
 * @since 1.0
 * 
 */

@Getter
@Setter
public class Exchange {
    private String name;
    private String url;
    private BigDecimal fees;
    private ExchangeType exchangeType;

    /**
     *
     * @param name         the name of the exchange
     * @param url          the URL of the exchange
     * @param fees         the trading fees charged by the exchange
     * @param exchangeType the type of the exchange (e.g., CEX, DEX)
     */
    public Exchange(String name, String url, BigDecimal fees, ExchangeType exchangeType) {
        this.name = name;
        this.url = url;
        this.fees = fees;
        this.exchangeType = exchangeType;
    }
}
