package com.arbibot.infra.adapter.forexchangecommunication.binance.models;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
public class ExecutionReportTest {
    @Test
    void testExecutionReportModel(@Value("__files/payload/binance/executionOrder1Buy.json") Resource executionReport)
            throws IOException {
        String json = StreamUtils.copyToString(executionReport.getInputStream(), StandardCharsets.UTF_8);
        ExecutionReport executionReportModel = new ObjectMapper().readValue(json, ExecutionReport.class);
        assertEquals("executionReport", executionReportModel.getEventType());
        assertEquals(1722263514064L, executionReportModel.getEventTime());
        assertEquals("MATICUSDT", executionReportModel.getSymbol());
        assertEquals("web_6287f4e877544358bd90ca61b6fab142", executionReportModel.getClientOrderId());
        assertEquals("BUY", executionReportModel.getSide());
        assertEquals("MARKET", executionReportModel.getOrderType());
        assertEquals("GTC", executionReportModel.getTimeInForce());
        assertEquals("14.80000000", executionReportModel.getQuantity());
        assertEquals("0.00000000", executionReportModel.getPrice());
        assertEquals("0.00000000", executionReportModel.getStopPrice());
        assertEquals("0.00000000", executionReportModel.getIcebergQty());
        assertEquals(-1, executionReportModel.getG());
        assertEquals("", executionReportModel.getC());
        assertEquals("NEW", executionReportModel.getExecutionType());
        assertEquals("NEW", executionReportModel.getOrderStatus());
        assertEquals("NONE", executionReportModel.getOrderRejectReason());
        assertEquals(3989775679L, executionReportModel.getOrderId());
        assertEquals("0.00000000", executionReportModel.getLastExecutedQuantity());
        assertEquals("0.00000000", executionReportModel.getCumulativeFilledQuantity());
        assertEquals("0.00000000", executionReportModel.getLastExecutedPrice());
        assertEquals("0", executionReportModel.getCommissionAmount());
        assertEquals(null, executionReportModel.getCommissionAsset());
        assertEquals(1722263514064L, executionReportModel.getTradeTime());
        assertEquals(-1, executionReportModel.getTradeId());
        assertEquals(8380544023L, executionReportModel.getI());
        assertEquals(true, executionReportModel.isW());
        assertEquals(false, executionReportModel.isM());
        assertEquals(false, executionReportModel.isMarketMaker());
        assertEquals(1722263514064L, executionReportModel.getOrderCreationTime());
        assertEquals("0.00000000", executionReportModel.getZ());
        assertEquals("0.00000000", executionReportModel.getY());
        assertEquals("7.75520000", executionReportModel.getQ());
        assertEquals("EXPIRE_MAKER", executionReportModel.getV());
    }
}
