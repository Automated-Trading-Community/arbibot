package com.arbibot.ports.output;

import com.arbibot.entities.Order;
import com.arbibot.entities.Pair;
import com.arbibot.entities.Exchange;

public interface ForExchangeCommunication {

    /**
     * 
     * Recover the price for a Pair.
     * 
     * @return {@code BigDecimal}
     */
    void getPriceForPair(Pair pair, Exchange exchange);

    /**
     * Pass orders and update their status.
     * 
     * @see com.arbibot.entities.Order
     */
    void passOrders(Order[] orders);
}