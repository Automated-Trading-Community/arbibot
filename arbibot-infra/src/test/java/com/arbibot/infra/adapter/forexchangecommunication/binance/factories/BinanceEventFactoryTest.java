package com.arbibot.infra.adapter.forexchangecommunication.binance.factories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;

import com.arbibot.infra.adapter.forexchangecommunication.binance.models.ExecutionReport;
import com.arbibot.infra.adapter.forexchangecommunication.binance.models.PositionBinance;
import com.arbibot.infra.adapter.forexchangecommunication.binance.models.UserDataEventBinance;

@SpringBootTest
class BinanceEventFactoryTest {

    @Test
    void testCreateExecutionReportEvent(@Value("__files/payload/binance/executionOrder1Buy.json") Resource exResource)
            throws IOException {
        String json = StreamUtils.copyToString(exResource.getInputStream(), StandardCharsets.UTF_8);
        UserDataEventBinance event = BinanceEventFactory.createEvent(json);

        assertNotNull(event);
        assertTrue(event instanceof ExecutionReport);
        ExecutionReport executionReport = (ExecutionReport) event;
        assertEquals("executionReport", executionReport.getEventType());
        assertEquals("MATICUSDT", executionReport.getSymbol());
    }

    @Test
    void testCreatePositionBinanceEvent(@Value("__files/payload/binance/position.json") Resource posResource)
            throws IOException {
        String json = StreamUtils.copyToString(posResource.getInputStream(), StandardCharsets.UTF_8);
        UserDataEventBinance event = BinanceEventFactory.createEvent(json);
        System.out.println(event.getClass());
        assertNotNull(event);
        assertTrue(event instanceof PositionBinance);
        PositionBinance positionBinance = (PositionBinance) event;
        assertEquals("outboundAccountPosition", positionBinance.getEventType());
    }

    @Test
    void notKnownEvent(@Value("__files/payload/binance/notKnownEvent.json") Resource notKnow) throws IOException {
        String json = StreamUtils.copyToString(notKnow.getInputStream(), StandardCharsets.UTF_8);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> BinanceEventFactory.createEvent(json));
        assertEquals(exception.getMessage(), "Unknown type: notKnownEvent");
    }
}