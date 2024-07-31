package com.arbibot.infra.adapter.fortriangulararbitraging.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/ping")
    public String getMethodName() {
        return "Pong";
    }
}
