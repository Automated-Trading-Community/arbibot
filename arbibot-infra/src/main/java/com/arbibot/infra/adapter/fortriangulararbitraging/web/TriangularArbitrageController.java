package com.arbibot.infra.adapter.fortriangulararbitraging.web;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.arbibot.core.entities.Asset;
import com.arbibot.core.entities.Exchange;
import com.arbibot.core.entities.Pair;
import com.arbibot.core.exceptions.TriangularArbitragingException;
import com.arbibot.core.usecases.arbitrage.TriangularArbitrage;

@RestController
public class TriangularArbitrageController {
    @Autowired
    private TriangularArbitrage arbitrage;

    @GetMapping("/triangularArbitrage")
    public String triangularArbitrage(@RequestParam String p1, @RequestParam String p2, @RequestParam String p3, @RequestParam String ex, @RequestParam float q) throws TriangularArbitragingException {
        Pair pair1, pair2, pair3;
        // TODO : comment retrouver les autres paramètres ?
        Exchange exchange = new Exchange(ex, null, null, null);

        BigDecimal bigDecimal = BigDecimal.valueOf(q);

        // TODO : ne pas créer deux fois le mêmes assets ?
        String[] split = p1.split("_");
        pair1 = new Pair(new Asset(split[0]), new Asset(split[1]));

        split = p2.split("_");
        pair2 = new Pair(new Asset(split[0]), new Asset(split[1]));

        split = p3.split("_");
        pair3 = new Pair(new Asset(split[0]), new Asset(split[1]));

        this.arbitrage.performTriangualarArbitrage(pair1, pair2, pair3, exchange, bigDecimal);

        return "ok";
    }

    @GetMapping("/ping")
    public String getMethodName() {
        return "Pong";
    }
    
}
