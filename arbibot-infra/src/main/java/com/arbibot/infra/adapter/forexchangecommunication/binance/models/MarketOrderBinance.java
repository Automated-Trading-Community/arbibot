package com.arbibot.infra.adapter.forexchangecommunication.binance.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

/**
 * Stock market order response from Binance Exchange
 * 
 * @author SChoumiloff
 * @author SebastienGuillemin
 * @since 1.0
 */
@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class MarketOrderBinance {

    @JsonProperty("symbol")
    private String symbol;

    @JsonProperty("orderId")
    private long orderId;

    @JsonProperty("orderListId")
    private int orderListId;

    @JsonProperty("clientOrderId")
    private String clientOrderId;

    @JsonProperty("transactTime")
    private long transactTime;

    @JsonProperty("price")
    private String price;

    @JsonProperty("origQty")
    private String origQty;

    @JsonProperty("executedQty")
    private String executedQty;

    @JsonProperty("cummulativeQuoteQty")
    private String cummulativeQuoteQty;

    @JsonProperty("status")
    private String status;

    @JsonProperty("timeInForce")
    private String timeInForce;

    @JsonProperty("type")
    private String type;

    @JsonProperty("side")
    private String side;

    @JsonProperty("workingTime")
    private long workingTime;

    @JsonProperty("fills")
    private List<Fill> fills;

    @JsonProperty("selfTradePreventionMode")
    private String selfTradePreventionMode;

    @Getter
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Fill {
        @JsonProperty("price")
        private String price;

        @JsonProperty("qty")
        private String qty;

        @JsonProperty("commission")
        private String commission;

        @JsonProperty("commissionAsset")
        private String commissionAsset;

        @JsonProperty("tradeId")
        private long tradeId;
    }
}
