package com.arbibot.ports.output;

import java.math.BigDecimal;

import com.arbibot.entities.Order;
import com.arbibot.entities.Pair;

public interface ForExchangeDataRecovery {

    /**
     * 
     * Recover the price for a Pair.
     * 
     * @param pair
     * @return {@code BigDecimal}
     */
    BigDecimal getPriceForPair(Pair pair);

    /**
     * TODO : je propose qu'on retourne le statut de l'ordre (SUCCESS, REJECTED etc.)
     * 
     * @param order
     */
    void passOrder(Order order);
}