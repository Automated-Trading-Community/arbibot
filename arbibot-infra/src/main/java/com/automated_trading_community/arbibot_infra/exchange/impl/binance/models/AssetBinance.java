package com.automated_trading_community.arbibot_infra.exchange.impl.binance.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@ToString
public class AssetBinance {
    @JsonProperty("asset")
    private String asset;

    @JsonProperty("free")
    private String free;

    @JsonProperty("locked")
    private String locked;
}
