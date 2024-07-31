package com.arbibot.core.exceptions.entities.pair;

import com.arbibot.core.entities.Asset;

/**
 * Exception thrown when the two {@link com.arbibot.core.entities.Asset} used to
 * create a {@link com.arbibot.core.entities.Pair} are the same.
 *
 * @see com.arbibot.core.entities.Pair
 * @see com.arbibot.core.entities.Asset
 * 
 * 
 * @author SChoumiloff
 * @author SebastienGuillemin
 * @since 1.0
 */
public class PairSameAssetException extends RuntimeException {

    /**
     * Constructs a new {@code PairSameAssetException} with the specified detail
     * message.
     *
     * @param asset1 first asset of the pair for which the error occurs.
     * @param asset2 second asset of the pair for which the error occurs.
     */
    public PairSameAssetException(Asset asset1, Asset asset2) {
        super("The two assets (" + asset1.getName() + " and " + asset2.getName()
                + " ) used to create a pair must be different.");
    }
}