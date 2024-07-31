package com.arbibot.infra.adapter.forexchangecommunication.binance.models;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
public class ApiPermissionBinanceTest {
    @Test
    void testApiPermissionTest() throws IOException {
        Resource permissionPayload = new ClassPathResource("__files/payload/binance/permissionNotValid.json");
        String json = StreamUtils.copyToString(permissionPayload.getInputStream(), StandardCharsets.UTF_8);
        ApiPermissionsBinance permissions = new ObjectMapper().readValue(json, ApiPermissionsBinance.class);

        assertEquals(permissions.isIpRestrict(), false);
        assertEquals(permissions.isEnableReading(), true);
        assertEquals(permissions.isEnableSpotAndMarginTrading(), false);
        assertEquals(permissions.isEnableWithdrawals(), false);
        assertEquals(permissions.isEnableInternalTransfer(), false);
        assertEquals(permissions.isEnableMargin(), false);
        assertEquals(permissions.isEnableFutures(), false);
        assertEquals(permissions.isPermitsUniversalTransfer(), false);
        assertEquals(permissions.isEnableVanillaOptions(), false);
        assertEquals(permissions.isEnablePortfolioMarginTrading(), false);
    }
}
