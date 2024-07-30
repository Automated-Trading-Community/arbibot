package com.arbibot.ports.input;

import java.math.BigDecimal;

import com.arbibot.entities.Pair;
import com.arbibot.exceptions.TriangularArbitragingException;
import com.arbibot.entities.Exchange;

/**
 * Interface exposing methods to perform triangular arbitrage.
 * 
 * @author SChoumiloff
 * @author SebastienGuillemin
 * @since 1.0
 */
public interface ForTriangularArbitraging {

    /**
     * Method to perform triangular arbitrage.
     * 
     * @param pair1    Initial pair of assets (eg. A/B).
     * @param pair2    Middle pair that serves as a bridge between the first
     *                 and the third asset (eg. B/C).
     * @param pair3    Final pair the is used to convert the middle asset back
     *                 to the first asset (eg. (C/A)).
     * @param exchange The exchange where orders are executed.
     * @param quantity Th equantity to buy with the first order of the arbitrage.
     * 
     * @throws TriangularArbitragingException is an error occurs during the
     *                                        arbitrage.
     */
    void performTriangualarArbitrage(Pair pair1, Pair pair2, Pair pair3, Exchange exchange, BigDecimal quantity)
            throws TriangularArbitragingException;
}
