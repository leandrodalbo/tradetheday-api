package com.open.trade.exchangecall;

import com.open.trade.configuration.BinanceProps;
import com.open.trade.data.Candle;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;

@Component
public class BinanceCall extends ExchangeCall {
    private static final String KLINES = "/api/v3/klines";

    public BinanceCall(WebClient.Builder builder, BinanceProps props) {
        super(builder.baseUrl(props.binanceUri()).build());
    }

    public Candle[] engulfingCandles(String symbol, String interval, Integer limit) {
        return engulfingToArray(Objects.requireNonNull(client.get()
                .uri(
                        builder -> builder.path(KLINES)
                                .queryParam("symbol", symbol)
                                .queryParam("interval", interval)
                                .queryParam("limit", limit)
                                .build())
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(List.class)
                .block()));
    }
}
