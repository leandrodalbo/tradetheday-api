package com.open.trade.exchangecall;

import com.open.trade.configuration.WebClientProvider;
import com.open.trade.model.Speed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class BinanceCall extends ExchangeCall {
    private static final String KLINES = "/api/v3/klines";
    private final Logger logger = LoggerFactory.getLogger(BinanceCall.class);
    private final int TOTAL_CANDLES = 3;

    public BinanceCall(WebClientProvider clientProvider) {
        super(clientProvider.binanceWebClient());
    }

    @Override
    public Mono engulfingCandles(String symbol, Speed speed) {
        return client.get()
                .uri(
                        builder -> builder.path(KLINES)
                                .queryParam("symbol", symbol)
                                .queryParam("interval", interval(speed))
                                .queryParam("limit", TOTAL_CANDLES)
                                .build())
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(List.class)
                .map(ExchangeCall::engulfingToArray)
                .doOnError(e -> {
                    logger.warn(e.getMessage());
                    logger.warn(String.format("Binance call failed fetching symbol: %s at speedL %s", symbol, speed));
                });
    }

    private String interval(Speed speed) {
        return switch (speed) {
            case HIGH -> "1h";
            case MEDIUM -> "4h";
            default -> "1d";
        };
    }
}
