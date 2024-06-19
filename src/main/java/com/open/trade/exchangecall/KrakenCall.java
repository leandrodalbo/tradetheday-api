package com.open.trade.exchangecall;

import com.open.trade.configuration.WebClientProvider;
import com.open.trade.exchanging.Candle;
import com.open.trade.exchanging.kraken.KrakenOrderPost;
import com.open.trade.exchanging.kraken.KrakenPostResult;
import com.open.trade.exchanging.kraken.KrakenResponse;
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
    private static final String PRIVATE_URL = "/0/private/AddOrder";
    private static final String OHLC_URL = "/0/public/OHLC";
    private static final String TICKER_URL = "0/public/Ticker";
    private static final String API_KEY_HEADER = "API-Key";
    private static final String API_SIGN_HEADER = "API-Sign";
    private static final String POST_CONTENT_TYPE_HEADER = "Content-Type";
    private static final String POST_CONTENT_TYPE_HEADER_VALUE = "application/x-www-form-urlencoded";

    private final Logger logger = LoggerFactory.getLogger(KrakenCall.class);

    public KrakenCall(WebClientProvider clientProvider) {
        super(clientProvider.krakenWebClient());
    }

    public Mono<Double> latestPrice(String symbol) {
        return client.get()
                .uri(builder -> builder.path(TICKER_URL)
                        .queryParam("pair", symbol)
                        .build())
                .retrieve()
                .bodyToMono(KrakenResponse.class)
                .map(it -> {
                    if (it.error().length > 0) {
                        logger.warn(String.format("Kraken ticker call failed for symbol %s", symbol));
                    }

                    Map data = (Map) it.result();
                    Map pairMap = (Map) data.get(symbol);
                    List info = (List) pairMap.get("c");
                    return Double.valueOf((String) info.get(0));
                })
                .doOnError(e -> logger.warn(e.getMessage()));
    }

    @Override
    public Mono<Candle[]> engulfingCandles(String symbol, Speed speed) {
        return client.get()
                .uri(
                        builder -> builder.path(OHLC_URL)
                                .queryParam("pair", symbol)
                                .queryParam("interval", interval(speed))
                                .queryParam("since", sinceParameter(speed))
                                .build()
                )
                .retrieve()
                .bodyToMono(KrakenResponse.class)
                .map(it -> {
                    if (it.error().length > 0) {
                        logger.warn(String.format("Kraken engulfing candles fetch for symbol: %s at speed %s", symbol, speed));
                    }

                    Map data = (Map) it.result();
                    return engulfingToArray((List) data.get(symbol));

                })
                .doOnError(e -> logger.warn(e.getMessage()));
    }

    public Mono<KrakenPostResult> postOrder(KrakenOrderPost orderPost) {
        return client.post()
                .uri(uriBuilder -> uriBuilder.path(PRIVATE_URL).build())
                .header(API_KEY_HEADER, orderPost.apiKey())
                .header(API_SIGN_HEADER, orderPost.signature())
                .header(POST_CONTENT_TYPE_HEADER, POST_CONTENT_TYPE_HEADER_VALUE)
                .bodyValue(orderPost.data())
                .retrieve()
                .bodyToMono(Map.class)
                .map(it -> {
                    Map response = it;
                    if (!((List) response.get("error")).isEmpty()) {
                        return new KrakenPostResult(false, "Kraken order failed");
                    }
                    return new KrakenPostResult(true, String.format("Kraken Order Created %s", orderPost.data()));

                })
                .doOnError(e -> logger.warn(e.getMessage()));
    }

    private long sinceParameter(Speed speed) {
        return switch (speed) {
            case HIGH -> Instant.now().minus(3, ChronoUnit.HOURS).getEpochSecond();
            case MEDIUM -> Instant.now().minus(12, ChronoUnit.HOURS).getEpochSecond();
            default -> Instant.now().minus(3, ChronoUnit.DAYS).getEpochSecond();
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
