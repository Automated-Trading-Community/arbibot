package com.arbibot.ports.input;

import java.math.BigDecimal;

import com.arbibot.entities.Pair;
import com.arbibot.usecases.arbitrage.exceptions.TriangularArbitragingException;
import com.arbibot.entities.Exchange;

/**
 * Interface exposing methods to perform triangular arbitrage.
 */
public interface ForTriangularArbitraging {

    /**
     * Method to perform triangular arbitrage.
     * 
     * @param p1       Initial pair of assets (eg. A/B).
     * @param p2       Middle pair that serves as a bridge between the first
     *                 and the third asset (eg. B/C).
     * @param p3       Final pair the is used to convert the middle asset back
     *                 to the first asset (eg. (C/A)).
     * @param exchange The exchange where orders are executed.
     * @param quantity
     * 
     * @throws TriangularArbitragingException
     */
    void performTriangualarArbitrage(Pair p1, Pair p2, Pair p3, Exchange exchange, BigDecimal quantity)
            throws TriangularArbitragingException;
}