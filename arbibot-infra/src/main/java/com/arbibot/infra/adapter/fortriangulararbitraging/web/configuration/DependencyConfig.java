package com.arbibot.infra.adapter.fortriangulararbitraging.web.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.arbibot.core.ports.output.ForExchangeCommunication;
import com.arbibot.core.usecases.arbitrage.TriangularArbitrage;

/**
 * TODO : doc + à déplacer ?
 * 
 * @author SChoumiloff
 * @author SebastienGuillemin
 * @since 1.0
 */
@Configuration
@ComponentScan(basePackages = { "com.arbibot" })
public class DependencyConfig {
    @Autowired
    private ForExchangeCommunication forExchangeCommunication;

    @Bean
    public TriangularArbitrage triangularArbitrage() {
        return new TriangularArbitrage(forExchangeCommunication);
    }
}
