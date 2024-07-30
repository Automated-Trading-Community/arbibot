package com.arbibot.infra.adapter.forexchangecommunication.binance.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
public class PositionTest {
    @Test
    public void testPositionBinance(@Value("__files/payload/binance/position.json") Resource positionBinanceResource)
            throws IOException {

        String json = StreamUtils.copyToString(positionBinanceResource.getInputStream(), StandardCharsets.UTF_8);
        PositionBinance positionBinance = new ObjectMapper().readValue(json, PositionBinance.class);

        assertEquals("outboundAccountPosition", positionBinance.getEventType());
        assertEquals(1722263439717L, positionBinance.getEventTime());
        assertEquals(1722263439717L, positionBinance.getLastAccountUpdate());

        List<Balance> balances = positionBinance.getBalances();
        assertEquals(3, balances.size());
        assertEquals("BNB", balances.get(0).getAsset());
        assertEquals("0.00008949", balances.get(0).getFree());
        assertEquals("0.00000000", balances.get(0).getLocked());

        assertEquals("USDT", balances.get(1).getAsset());
        assertEquals("7.80242023", balances.get(1).getFree());
        assertEquals("0.00000000", balances.get(1).getLocked());

        assertEquals("MATIC", balances.get(2).getAsset());
        assertEquals("0.04094182", balances.get(2).getFree());
        assertEquals("0.00000000", balances.get(2).getLocked());
    }
}
