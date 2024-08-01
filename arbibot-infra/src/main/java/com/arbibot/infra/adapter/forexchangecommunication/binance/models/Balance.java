package com.arbibot.infra.adapter.forexchangecommunication.binance.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 
 * This class represents a Binance balance. It's use in binance socket response.
 * 
 * @see com.arbibot.infra.adapter.forexchangecommunication.binance.models.PositionBinance
 * @see com.arbibot.infra.adapter.forexchangecommunication.binance.models.Balance
 * 
 * @author SChoumiloff
 * @author SebastienGuillemin
 * @since 1.0
 */
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