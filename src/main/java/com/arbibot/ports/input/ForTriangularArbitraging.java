package com.arbibot.ports.input;

import java.math.BigDecimal;

import com.arbibot.entities.Asset;
import com.arbibot.entities.Exchange;

/**
 * Pour nommer l'interface, j'ai repris les mêmes noms que j'ai vu plusieurs endroits
 * (ex: https://jmgarridopaz.github.io/content/hexagonalarchitecture-ig/chapter1.html#tc3-2)
 * 
 * Il semble que la convention For[Quelque chose] est celle proposée par le créateur l'architecture (à vérifier)
 * (src: https://dev.to/xoubaman/understanding-hexagonal-architecture-3gk)
 */
public interface ForTriangularArbitraging extends ForArbitrage {

    // J'ai mis les paramètres un peu au feeling mais il faut sûrement les modifier.
    void performTriangualarArbitrage(Asset asset1, Asset asset2, Asset asset3, Exchange exchange, BigDecimal quentity);
}
