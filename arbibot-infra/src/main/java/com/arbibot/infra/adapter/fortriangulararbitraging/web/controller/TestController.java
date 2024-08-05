package com.arbibot.infra.adapter.fortriangulararbitraging.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.arbibot.core.entities.Asset;
import com.arbibot.core.entities.Pair;
import com.arbibot.infra.adapter.forexchangecommunication.binance.Binance;


/**
 * TODO : controller to test if the web server is up.
 * 
 * @author SChoumiloff
 * @author SebastienGuillemin
 * @since 1.0
 */
@RestController
public class TestController {
    /**
     * 
     * @return "Pong" when called.
     */
    @GetMapping("/ping")
    public String getMethodName() {
        return "Pong";
    }

    @GetMapping("/price")
    public String getProceForPair(@RequestParam String asset1, @RequestParam String asset2, Binance binance) {
        Pair pair = new Pair(new Asset(asset1), new Asset(asset2));
        binance.getPriceForPair(pair);

        return pair.getPrice().toString();
    }
}
