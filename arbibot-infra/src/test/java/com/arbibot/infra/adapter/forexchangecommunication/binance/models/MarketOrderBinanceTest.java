package com.arbibot.infra.adapter.forexchangecommunication.binance.models;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MarketOrderBinanceTest {

    @Test
    void testMarketOrderBuyBinance() throws IOException {
        Resource orderBinanceBuy = new ClassPathResource("__files/payload/binance/marketOrderBuyBinance.json");
        String json = StreamUtils.copyToString(orderBinanceBuy.getInputStream(), StandardCharsets.UTF_8);

        ObjectMapper objectMapper = new ObjectMapper();
        MarketOrderBinance miniTicker = objectMapper.readValue(json, MarketOrderBinance.class);

        // TODO assertEquals
    }

}
