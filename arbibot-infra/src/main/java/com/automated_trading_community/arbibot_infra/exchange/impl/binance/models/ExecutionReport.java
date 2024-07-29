package com.automated_trading_community.arbibot_infra.exchange.impl.binance.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExecutionReport extends UserDataEventBinance {

    @JsonProperty("e")
    private String eventType;

    @JsonProperty("E")
    private long eventTime;

    @JsonProperty("s")
    private String symbol;

    @JsonProperty("c")
    private String clientOrderId;

    @JsonProperty("S")
    private String side;

    @JsonProperty("o")
    private String orderType;

    @JsonProperty("f")
    private String timeInForce;

    @JsonProperty("q")
    private String quantity;

    @JsonProperty("p")
    private String price;

    @JsonProperty("P")
    private String stopPrice;

    @JsonProperty("F")
    private String icebergQty;

    @JsonProperty("g")
    private int g;

    @JsonProperty("C")
    private String c;

    @JsonProperty("x")
    private String executionType;

    @JsonProperty("X")
    private String orderStatus;

    @JsonProperty("r")
    private String orderRejectReason;

    @JsonProperty("i")
    private long orderId;

    @JsonProperty("l")
    private String lastExecutedQuantity;

    @JsonProperty("z")
    private String cumulativeFilledQuantity;

    @JsonProperty("L")
    private String lastExecutedPrice;

    @JsonProperty("n")
    private String commissionAmount;

    @JsonProperty("N")
    private String commissionAsset;

    @JsonProperty("T")
    private long tradeTime;

    @JsonProperty("t")
    private int tradeId;

    @JsonProperty("I")
    private long i;

    @JsonProperty("w")
    private boolean w;

    @JsonProperty("m")
    private boolean m;

    @JsonProperty("M")
    private boolean marketMaker;

    @JsonProperty("O")
    private long orderCreationTime;

    @JsonProperty("Z")
    private String z;

    @JsonProperty("Y")
    private String y;

    @JsonProperty("Q")
    private String q;

    @JsonProperty("V")
    private String v;
}