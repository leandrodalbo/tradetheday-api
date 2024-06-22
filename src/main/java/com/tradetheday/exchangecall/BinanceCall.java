package com.tradetheday.exchangecall;

import com.tradetheday.configuration.WebClientProvider;
import com.tradetheday.exchanging.Candle;
import com.tradetheday.model.Timeframe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Component
public class BinanceCall extends ExchangeCall {
    private static final String KLINES = "/api/v3/klines";
    private static final int TOTAL_CANDLES = 3;

    private final Logger logger = LoggerFactory.getLogger(BinanceCall.class);

    public BinanceCall(WebClientProvider clientProvider) {
        super(clientProvider.binanceWebClient());
    }

    @Override
    public Mono<Candle[]> engulfingCandles(String symbol, Timeframe tf) {
        return fetchOHLC(symbol, tf, Optional.empty());
    }

    @Override
    public Mono<Candle[]> MACandles(String symbol, Timeframe speed, int longestPeriod) {
        return fetchOHLC(symbol, speed, Optional.of(longestPeriod));
    }

    private Mono<Candle[]> fetchOHLC(String symbol, Timeframe speed, Optional<Integer> period) {
        return client.get()
                .uri(
                        builder -> builder.path(KLINES)
                                .queryParam("symbol", symbol)
                                .queryParam("interval", interval(speed))
                                .queryParam("limit", period.orElse(TOTAL_CANDLES))
                                .build())
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(List.class)
                .map(ExchangeCall::engulfingToArray)
                .doOnError(e -> logger.info(e.getMessage()));
    }

    private String interval(Timeframe speed) {
        return switch (speed) {
            case H1 -> "1h";
            case H4 -> "4h";
            default -> "1d";
        };
    }
}
