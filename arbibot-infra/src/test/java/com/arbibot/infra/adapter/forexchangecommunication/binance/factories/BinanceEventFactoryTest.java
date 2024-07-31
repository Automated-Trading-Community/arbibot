package com.arbibot.infra.adapter.forexchangecommunication.binance.factories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;

import com.arbibot.infra.adapter.forexchangecommunication.binance.models.ExecutionReport;
import com.arbibot.infra.adapter.forexchangecommunication.binance.models.PositionBinance;
import com.arbibot.infra.adapter.forexchangecommunication.binance.models.UserDataEventBinance;

@SpringBootTest
class BinanceEventFactoryTest {

    @Test
    void testCreateExecutionReportEvent() throws IOException {
        Resource exResource = new ClassPathResource("__files/payload/binance/executionOrder1Buy.json");
        String json = StreamUtils.copyToString(exResource.getInputStream(), StandardCharsets.UTF_8);
        UserDataEventBinance event = BinanceEventFactory.createEvent(json);

        assertNotNull(event);
        assertTrue(event instanceof ExecutionReport);
        ExecutionReport executionReport = (ExecutionReport) event;
        assertEquals("executionReport", executionReport.getEventType());
        assertEquals("MATICUSDT", executionReport.getSymbol());
    }

    @Test
    void testCreatePositionBinanceEvent() throws IOException {
        Resource posResource = new ClassPathResource("__files/payload/binance/position.json");

        String json = StreamUtils.copyToString(posResource.getInputStream(), StandardCharsets.UTF_8);
        UserDataEventBinance event = BinanceEventFactory.createEvent(json);
        System.out.println(event.getClass());
        assertNotNull(event);
        assertTrue(event instanceof PositionBinance);
        PositionBinance positionBinance = (PositionBinance) event;
        assertEquals("outboundAccountPosition", positionBinance.getEventType());
    }

    @Test
    void notKnownEvent() throws IOException {
        Resource notKnow = new ClassPathResource("__files/payload/binance/notKnownEvent.json");
        String json = StreamUtils.copyToString(notKnow.getInputStream(), StandardCharsets.UTF_8);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> BinanceEventFactory.createEvent(json));
        assertEquals(exception.getMessage(), "Unknown type: notKnownEvent");
    }
}