package com.arbibot.core.exceptions.entities.ports.output;

import com.arbibot.core.entities.Pair;

import lombok.Getter;

/**
 * Exception thrown when the price of a {@link com.arbibot.core.entities.Pair}
 * canot be retrieved.
 *
 * @see com.arbibot.core.entities.Pair
 * @see com.arbibot.core.entities.Asset
 * 
 * 
 * @author SChoumiloff
 * @author SebastienGuillemin
 * @since 1.0
 */
@Getter
public class ExchangePairPriceException extends Exception {
    private Pair pair;
    private String reason;

    /**
     * Constructs a new {@code PairPriceException} with the specified detail
     * message.
     * 
     * @param pair   The pair for which price retrieval has failed.
     * @param reason The reason for the failure
     */
    public ExchangePairPriceException(Pair pair, String reason) {
        super("Error when retrieving price of pair " + pair.toString() + ".\nReason : " + reason);

        this.pair = pair;
        this.reason = reason;
    }
}
