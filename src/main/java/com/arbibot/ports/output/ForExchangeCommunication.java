package com.arbibot.ports.output;

import com.arbibot.entities.Order;
import com.arbibot.entities.Pair;
import com.arbibot.entities.Exchange;

public interface ForExchangeCommunication {

    /**
     * 
     * Recover the price for a Pair.
     * 
     * @param pair
     * @return {@code BigDecimal}
     */
    void getPriceForPair(Pair pair, Exchange exchange);

    /**
     * TODO : je propose qu'on retourne le statut de l'ordre (SUCCESS, REJECTED
     * etc.)
     * 
     * @param order
     */
    void passOrders(Order[] orders);
}