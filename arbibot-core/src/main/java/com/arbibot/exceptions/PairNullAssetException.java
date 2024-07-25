package com.arbibot.exceptions;

/**
 * Exception thrown when an {@link com.arbibot.entities.Asset} is missing when
 * creating a Pair.
 *
 * @see com.arbibot.entities.Pair
 * @see com.arbibot.entities.Asset
 * 
 * @author SebastienGuillemin
 * @author SChoumiloff
 * @since 1.0
 */
public class PairNullAssetException extends RuntimeException {

    /**
     * Constructs a new {@code PairException} with the specified detail
     * message.
     *
     * @param errorMessage the detail message describing the validation error
     */
    public PairNullAssetException(String errorMessage) {
        super(errorMessage);
    }
}