package com.arbibot.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * The {@code Asset} class represents a financial asset in the domain.
 * An asset is any resource or item that holds value and can be traded in the
 * financial market.
 * Examples of assets include stocks, bonds, commodities, and currencies.
 * 
 * This class includes:
 * - A unique identifier for each asset instance, which is auto-generated.
 * - The name of the asset.
 * 
 * The {@code id} field is automatically generated using an UUID to ensure
 * that each asset instance has a unique identifier.
 * 
 * The {@code name} field represents the name of the asset and is accessible and
 * modifiable
 * via getter and setter methods generated by Lombok.
 * 
 * Example usage:
 * 
 * <pre>
 * {@code
 * Asset asset1 = Asset.create("Gold");
 * Asset asset2 = Asset.create("Silver");
 * Asset asset3 = Asset.create("BTC");
 * System.out.println("Asset 1 ID: " + asset1.getId());
 * System.out.println("Asset 2 ID: " + asset2.getId());
 * }
 * </pre>
 * 
 * @author SChoumiloff
 * @since 1.0
 */
@Getter
@Setter
public class Asset {
    private final UUID id = UUID.randomUUID();

    private String name;

    /**
     * Private constructor to create an {@code Asset} instance with a specified
     * name.
     * The {@code id} is auto-generated.
     *
     * @param name the name of the asset
     */
    public Asset(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Asset))
            return false;

        return this.name.equalsIgnoreCase(((Asset) obj).getName());
    }

    @Override
    public String toString() {
        return this.getName();
    }
}