package com.arbibot.infra.adapter.fortriangulararbitraging.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
