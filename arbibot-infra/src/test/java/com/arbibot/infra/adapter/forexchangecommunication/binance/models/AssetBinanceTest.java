package com.arbibot.infra.adapter.forexchangecommunication.binance.models;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
public class AssetBinanceTest {

    @Test
    void testAssetBinance(@Value("__files/payload/binance/assetBinance.json") Resource assetBinance)
            throws IOException {
        String json = StreamUtils.copyToString(assetBinance.getInputStream(),
                StandardCharsets.UTF_8);
        AssetBinance asset = new ObjectMapper().readValue(json, AssetBinance.class);
        assertEquals(asset.getAsset(), "BTC");
        assertEquals(asset.getFree(), "0.001");
        assertEquals(asset.getLocked(), "0.0001");
    }
}
