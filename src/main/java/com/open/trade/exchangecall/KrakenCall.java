package com.open.trade.exchangecall;

import com.open.trade.configuration.WebClientProvider;
import com.open.trade.data.Candle;
import com.open.trade.exchangecall.exchange.KrakenResponse;
import com.open.trade.model.Speed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

@Component
public class KrakenCall extends ExchangeCall {
    private static final String ON_PATH = "/0/public/OHLC";
    private final Logger logger = LoggerFactory.getLogger(KrakenCall.class);

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
                    Candle[] candles = new Candle[2];

                    Map data = (Map) it.result();

                    if (data != null) {
                        candles[0] = engulfingToArray((List) data.get(symbol))[0];
                        candles[1] = engulfingToArray((List) data.get(symbol))[1];
                    }

                    logger.info(String.format("| ENGULFING KRAKEN FETCH | -> Symbol: %s, %s ", symbol, candlesLogMessage(candles)));

                    return candles;
                })
                .doOnError(e -> {
                    logger.warn(e.getMessage());
                    logger.warn(String.format("Kraken call failed fetching symbol: %s at speedL %s", symbol, speed));
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
