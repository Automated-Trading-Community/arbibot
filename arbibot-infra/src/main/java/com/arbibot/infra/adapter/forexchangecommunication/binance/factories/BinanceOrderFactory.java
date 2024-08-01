package com.arbibot.infra.adapter.forexchangecommunication.binance.factories;

import java.util.LinkedHashMap;
import java.util.Map;

import com.arbibot.core.entities.Order;

/**
 * 
 * @author SChoumiloff SGuillemin
 * @since 1.0
 */
public class BinanceOrderFactory {

    /**
     * 
     * create Map parameter from Order Object
     * In a first way we will implement just market order
     * 
     * @see com.arbibot.core.entities.Order
     * 
     * @param order
     * @return
     */
    public static Map<String, Object> createOrderFromOrderObj(Order order) {
        Map<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("symbol", order.getPair().getSymbol());
        parameters.put("type", "MARKET");
        switch (order.getType()) {
            case SELL:
                parameters.put("side", "SELL");
                parameters.put("quantity", order.getQuantity().floatValue());
                break;
            case BUY:
                parameters.put("side", "BUY");
                parameters.put("quoteOrderQty", order.getQuantity().floatValue());
                break;
        }
        return parameters;
    }
}
