package com.automated_trading_community.arbibot_infra.exchange.impl.binance.factories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

import com.automated_trading_community.arbibot_infra.exchange.impl.binance.models.ExecutionReport;
import com.automated_trading_community.arbibot_infra.exchange.impl.binance.models.PositionBinance;
import com.automated_trading_community.arbibot_infra.exchange.impl.binance.models.UserDataEventBinance;

class BinanceEventFactoryTest {

    @Test
    void testCreateExecutionReportEvent() throws IOException {
        String json = loadJsonFromFile(
                "/Users/sachachoumiloff/Desktop/automated-trading-community/arbibot/arbibot-infra/src/test/ressources/files/payload/binance/executionOrder1Buy.json");
        UserDataEventBinance event = BinanceEventFactory.createEvent(json);

        assertNotNull(event);
        assertTrue(event instanceof ExecutionReport);
        ExecutionReport executionReport = (ExecutionReport) event;
        assertEquals("executionReport", executionReport.getEventType());
        assertEquals("MATICUSDT", executionReport.getSymbol());
    }

    @Test
    void testCreatePositionBinanceEvent() throws IOException {
        String json = loadJsonFromFile(
                "/Users/sachachoumiloff/Desktop/automated-trading-community/arbibot/arbibot-infra/src/test/ressources/files/payload/binance/position.json");
        UserDataEventBinance event = BinanceEventFactory.createEvent(json);

        assertNotNull(event);
        assertTrue(event instanceof PositionBinance);
        PositionBinance positionBinance = (PositionBinance) event;
        assertEquals("outboundAccountPosition", positionBinance.getEventType());
    }

    private String loadJsonFromFile(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
    }
}