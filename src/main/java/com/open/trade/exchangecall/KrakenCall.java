package com.open.trade.exchangecall;

import com.open.trade.configuration.WebClientProvider;
import com.open.trade.data.Candle;
import com.open.trade.exchangecall.exchange.KrakenResponse;
import com.open.trade.model.Speed;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

@Component
public class KrakenCall extends ExchangeCall {
    private static final String ON_PATH = "/0/public/OHLC";

    public KrakenCall(WebClientProvider clientProvider) {
        super(clientProvider.krakenWebClient());
    }

    @Override
    public Mono<Candle[]> engulfingCandles(String symbol, Speed speed) {
        return client.get()
                .uri(
                        builder -> builder.path(ON_PATH)
                                .queryParam("pair", symbol)
                                .queryParam("interval", interval(speed))
                                .queryParam("since", sinceParameter(speed))
                                .build()
                )
                .retrieve()
                .bodyToMono(KrakenResponse.class)
                .map(it -> {
                    Map data = (Map) it.result();
                    return engulfingToArray((List) data.get(symbol));
                });
    }

    private long sinceParameter(Speed speed) {
        return switch (speed) {
            case HIGH -> Instant.now().minus(2, ChronoUnit.HOURS).getEpochSecond();
            case MEDIUM -> Instant.now().minus(8, ChronoUnit.HOURS).getEpochSecond();
            default -> Instant.now().minus(2, ChronoUnit.DAYS).getEpochSecond();
        };
    }

    private int interval(Speed speed) {
        return switch (speed) {
            case HIGH -> 60;
            case MEDIUM -> 240;
            default -> 1440;
        };
    }
}
