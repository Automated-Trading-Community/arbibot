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
public class AssetBinanceTest {

    @Test
    void testAssetBinance() throws IOException {
        Resource assetBinance = new ClassPathResource("__files/payload/binance/assetBinance.json");
        String json = StreamUtils.copyToString(assetBinance.getInputStream(),
                StandardCharsets.UTF_8);
        AssetBinance asset = new ObjectMapper().readValue(json, AssetBinance.class);
        assertEquals(asset.getAsset(), "BTC");
        assertEquals(asset.getFree(), "0.001");
        assertEquals(asset.getLocked(), "0.0001");
    }
}
