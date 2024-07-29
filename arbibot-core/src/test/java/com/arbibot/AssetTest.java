package com.arbibot;

import org.junit.jupiter.api.Test;

import com.arbibot.entities.Asset;

import static org.junit.jupiter.api.Assertions.*;

public class AssetTest {

    @Test
    public void testAssetCreation() {
        String assetName = "BTC";
        Asset asset = new Asset(assetName);

        assertNotNull(asset.getId(), "The asset ID should not be null.");
        assertEquals(assetName, asset.getName(), "The asset name should match the input name.");
    }

    @Test
    public void testUniqueAssetId() {
        Asset asset1 = new Asset("BTC");
        Asset asset2 = new Asset("ETH");

        assertNotNull(asset1.getId(), "The first asset ID should not be null.");
        assertNotNull(asset2.getId(), "The second asset ID should not be null.");
        assertNotEquals(asset1.getId(), asset2.getId(), "The asset IDs should be unique");
    }

    @Test
    public void testSetName() {
        String initialName = "BTC";
        String newName = "ETH";
        Asset asset = new Asset(initialName);

        assertEquals(initialName, asset.getName(), "The asset name should be initially set to the input name.");

        asset.setName(newName);
        assertEquals(newName, asset.getName(), "The asset name should be updated to the new name.");
    }

}