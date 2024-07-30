package com.automated_trading_community.arbibot_infra.exchange.impl.binance.models;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.ToString;

/**
 * 
 * Class use to represent a Binance mini ticker. It's use in binance socket
 * message.
 * 
 * @see com.binance.connector.client.WebSocketStreamClient#miniTickerStream
 * 
 * @author SChoumiloff SGuillemin
 * @since 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@ToString
public class MiniTicker {
    @JsonProperty("e")
    private String event;

    @JsonProperty("E")
    private long eventTime;

    @JsonProperty("s")
    private String symbol;

    @JsonProperty("c")
    private String closePrice;

    @JsonProperty("o")
    private String openPrice;

    @JsonProperty("h")
    private String highPrice;

    @JsonProperty("l")
    private String lowPrice;

    @JsonProperty("v")
    private String volume;

    @JsonProperty("q")
    private String quoteVolume;

    /**
     * 
     * cast to a BigDecimal value
     * 
     * @return {@code BigDecimal}
     */
    public BigDecimal getClosePrice() {
        return new BigDecimal(closePrice);
    }
}