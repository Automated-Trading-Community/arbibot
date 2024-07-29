package com.automated_trading_community.arbibot_infra.exchange.impl.binance.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Balance {

    @JsonProperty("a")
    private String asset;

    @JsonProperty("f")
    private String free;

    @JsonProperty("l")
    private String locked;
}