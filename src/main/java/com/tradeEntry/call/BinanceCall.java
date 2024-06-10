package com.tradeEntry.call;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class BinanceCall {
    private static final String KLINES = "/api/v3/klines";
    private final WebClient webClient;

    public BinanceCall(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<List> fetchCandles(String symbol, String interval, Integer limit) {

        return webClient.get()
                .uri(
                        builder -> builder.path(KLINES)
                                .queryParam("symbol", symbol)
                                .queryParam("interval", interval)
                                .queryParam("limit", limit)
                                .build())
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(List.class);

    }


}
