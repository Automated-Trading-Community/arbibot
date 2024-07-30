package com.automated_trading_community.arbibot_infra.exchange.impl.binance.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.ToString;

/**
 * This class represents a Binance asset. It's use in binance rest response.
 * 
 * @see com.binance.connector.client.SpotClient
 * 
 * @author SChoumiloff SGuillemin
 * @since 1.0
 */
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
