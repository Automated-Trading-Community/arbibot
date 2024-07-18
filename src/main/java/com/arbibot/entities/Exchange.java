package com.arbibot.entities;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.arbibot.exceptions.ExchangeException;

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
 * @since 1.0
 * 
 */

@Getter
@Setter
public class Exchange {

    private String name;
    private String url;
    private List<Pair> tradingPairs;
    private float fees;
    private Asset feesAsset;
    private ExchangeType exchangeType;

    /**
     * Constructs an Exchange instance with the specified parameters.
     *
     * @param name         the name of the exchange
     * @param url          the URL of the exchange
     * @param tradingPairs the list of trading pairs available on the exchange
     * @param fees         the trading fees charged by the exchange
     * @param feesAsset    the asset in which the fees are charged
     * @param exchangeType the type of the exchange (e.g., CEX,
     *                     DEX)
     */
    public Exchange(String name, String url, List<Pair> tradingPairs, float fees, Asset feesAsset,
            ExchangeType exchangeType) {
        this.name = name;
        this.url = url;
        this.tradingPairs = tradingPairs;
        this.fees = fees;
        this.feesAsset = feesAsset;
        this.exchangeType = exchangeType;
    }

    /**
     * Validates the exchange to ensure there are no multiple trading pairs.
     *
     * @throws ExchangeException if multiple trading pairs are detected
     */
    public void validateExchange() throws ExchangeException {
        Set<Pair> set = this.tradingPairs.stream().collect(Collectors.toSet());
        boolean isMultiplePairs = set.size() < this.tradingPairs.size();
        if (isMultiplePairs) {
            throw new ExchangeException("Multiple trading pairs are not allowed");
        }
    }

}
