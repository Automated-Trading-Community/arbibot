package com.arbibot.infra.adapter.forexchangecommunication.binance.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
public class MiniTickerTest {
    @Test
    public void testMiniTicker(@Value("__files/payload/binance/miniTickerBinance.json") Resource miniTickerResource)
            throws IOException {

        String json = StreamUtils.copyToString(miniTickerResource.getInputStream(), StandardCharsets.UTF_8);

        ObjectMapper objectMapper = new ObjectMapper();
        MiniTicker miniTicker = objectMapper.readValue(json, MiniTicker.class);

        assertEquals("miniTicker", miniTicker.getEvent());
        assertEquals(1622263514064L, miniTicker.getEventTime());
        assertEquals("BTCUSDT", miniTicker.getSymbol());
        assertEquals("35000.12", miniTicker.getClosePrice().toPlainString());
        assertEquals("34000.00", miniTicker.getOpenPrice());
        assertEquals("35500.00", miniTicker.getHighPrice());
        assertEquals("33500.00", miniTicker.getLowPrice());
        assertEquals("12345.67", miniTicker.getVolume());
        assertEquals("456789.12", miniTicker.getQuoteVolume());
    }
}