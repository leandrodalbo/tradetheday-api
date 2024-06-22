package com.tradetheday.configuration;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class WebClientProvider {

    private final WebClient.Builder builder;
    private final BinanceProps binanceProps;
    private final KrakenProps krakenProps;

    public WebClientProvider(WebClient.Builder builder, BinanceProps binanceProps, KrakenProps krakenProps) {
        this.builder = builder;
        this.binanceProps = binanceProps;
        this.krakenProps = krakenProps;
    }

    public WebClient binanceWebClient() {
        return builder.baseUrl(binanceProps.binanceUri()).build();
    }

    public WebClient krakenWebClient() {
        return builder.baseUrl(krakenProps.apiUri()).build();
    }
}
