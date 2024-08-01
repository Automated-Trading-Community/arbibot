package com.arbibot.infra.adapter.forexchangecommunication.binance.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

/**
 * TODO : doc
 * 
 * @author SChoumiloff
 * @author SebastienGuillemin
 * @since 1.0
 */
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PositionBinance extends UserDataEventBinance {

    @JsonProperty("e")
    private String eventType;

    @JsonProperty("E")
    private long eventTime;

    @JsonProperty("u")
    private long lastAccountUpdate;

    @JsonProperty("B")
    private List<Balance> balances;
}