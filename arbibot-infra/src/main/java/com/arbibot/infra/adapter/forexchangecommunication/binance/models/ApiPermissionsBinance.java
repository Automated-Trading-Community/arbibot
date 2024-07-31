package com.arbibot.infra.adapter.forexchangecommunication.binance.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@ToString
public class ApiPermissionsBinance {

    @JsonProperty("ipRestrict")
    private boolean ipRestrict;

    @JsonProperty("createTime")
    private long createTime;

    @JsonProperty("enableReading")
    private boolean enableReading;

    @JsonProperty("enableSpotAndMarginTrading")
    private boolean enableSpotAndMarginTrading;

    @JsonProperty("enableWithdrawals")
    private boolean enableWithdrawals;

    @JsonProperty("enableInternalTransfer")
    private boolean enableInternalTransfer;

    @JsonProperty("enableMargin")
    private boolean enableMargin;

    @JsonProperty("enableFutures")
    private boolean enableFutures;

    @JsonProperty("permitsUniversalTransfer")
    private boolean permitsUniversalTransfer;

    @JsonProperty("enableVanillaOptions")
    private boolean enableVanillaOptions;

    @JsonProperty("enablePortfolioMarginTrading")
    private boolean enablePortfolioMarginTrading;
}