package com.open.trade.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientProvider {

    @Bean
    WebClient binanceClient(WebClient.Builder builder, ProjectProps projectProps) {
        return builder.baseUrl(projectProps.binanceUri()).build();
    }

    @Bean
    WebClient krakenClient(WebClient.Builder builder, KrakenProps krakenProps) {
        return builder.baseUrl(krakenProps.apiUri()).build();
    }
}