package com.arbibot.infra.adapter.forexchangecommunication.binance.factories;

import java.io.IOException;

import com.arbibot.infra.adapter.forexchangecommunication.binance.models.BinanceEventType;
import com.arbibot.infra.adapter.forexchangecommunication.binance.models.ExecutionReport;
import com.arbibot.infra.adapter.forexchangecommunication.binance.models.PositionBinance;
import com.arbibot.infra.adapter.forexchangecommunication.binance.models.UserDataEventBinance;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Factory class to create Binance event objects from JSON strings.
 * 
 * @author Schoumiloff SGuillemin
 * @since 1.0
 * 
 * @see com.arbibot.infra.adapter.forexchangecommunication.binance.models.PositionBinance
 * @see com.arbibot.infra.adapter.forexchangecommunication.binance.models.ExecutionReport
 */
public class BinanceEventFactory {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Creates a Binance event object from the given JSON string.
     * This method determines the type of event based on the "e" field in the JSON
     * and returns an appropriate object.
     *
     * @param json the JSON string representing the Binance event
     * @return an instance of {@link UserDataEventBinance} which can be either
     *         {@link ExecutionReport} or {@link PositionBinance} depending on the
     *         event type
     * @throws IOException              if there is an error reading the JSON string
     * @throws IllegalArgumentException if the event type in the JSON string is
     *                                  unsupported
     */
    public static UserDataEventBinance createEvent(String json) throws IOException {
        JsonNode rootNode = objectMapper.readTree(json);
        String eventType = rootNode.get("e").asText();

        BinanceEventType type = BinanceEventType.fromType(eventType);

        switch (type) {
            case EXECUTION_REPORT:
                return objectMapper.treeToValue(rootNode, ExecutionReport.class);
            case OUTBOUND_ACCOUNT_POSITION:
                return objectMapper.treeToValue(rootNode, PositionBinance.class);
            default:
                throw new IllegalArgumentException("Unsupported event type: " + eventType);
        }
    }
}
