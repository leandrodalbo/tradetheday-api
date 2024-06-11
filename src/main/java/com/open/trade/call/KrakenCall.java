package com.open.trade.call;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
public class KrakenCall extends ExchangeCall {
    private static final String KLINES = "/api/v3/klines";

    public KrakenCall(WebClient krakenClient) {
        super(krakenClient);
    }

    @Override
    public void searchEngulfingEntries(String symbol, String interval) {

    }

    private List fetchCandles(String symbol, String interval, Integer limit) {

        return client.get()
                .uri(
                        builder -> builder.path(KLINES)
                                .queryParam("symbol", symbol)
                                .queryParam("interval", interval)
                                .queryParam("limit", limit)
                                .build())
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(List.class)
                .block();

    }
}
