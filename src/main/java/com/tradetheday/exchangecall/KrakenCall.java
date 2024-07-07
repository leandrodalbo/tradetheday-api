package com.tradetheday.exchangecall;

import com.tradetheday.configuration.WebClientProvider;
import com.tradetheday.exchanging.Candle;
import com.tradetheday.exchanging.kraken.KrakenOrderPost;
import com.tradetheday.exchanging.kraken.KrakenPostResult;
import com.tradetheday.exchanging.kraken.KrakenResponse;
import com.tradetheday.messages.Messages;
import com.tradetheday.model.Timeframe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @Override
    public Mono<Candle[]> engulfingCandles(String symbol, Timeframe tf) {
        return fetchOHLC(symbol, tf, Optional.empty());
    }

    @Override
    public Mono<Candle[]> MACandles(String symbol, Timeframe tf, int longestPeriod) {
        return fetchOHLC(symbol, tf, Optional.of(longestPeriod));
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
                        logger.info(String.format("%s:%s", Messages.KRAKEN_FETCH_FAILED, symbol));
                    }

                    Map data = (Map) it.result();
                    Map pairMap = (Map) data.get(symbol);
                    List info = (List) pairMap.get("c");
                    return Double.valueOf((String) info.get(0));
                })
                .doOnError(e -> logger.info(e.getMessage()));
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
                    KrakenPostResult result = null;
                    Map response = it;

                    List errors = (List) response.get("error");

                    if (!errors.isEmpty()) {
                        result = new KrakenPostResult(false, String.format("%s:%s", Messages.KRAKEN_ORDER_FAILED, errors.get(0).toString()));
                    } else {
                        result = new KrakenPostResult(true, String.format("%s:%s", Messages.KRAKEN_ORDER_SUCCESS, orderPost.data()));
                    }

                    logger.info(result.message());
                    return result;
                })
                .doOnError(e -> logger.info(e.getMessage()));
    }

    private Mono<Candle[]> fetchOHLC(String symbol, Timeframe tf, Optional<Integer> period) {
        return client.get()
                .uri(
                        builder -> builder.path(OHLC_URL)
                                .queryParam("pair", symbol)
                                .queryParam("interval", interval(tf))
                                .queryParam("since", period.map(integer ->
                                                sinceParameter(tf, integer))
                                        .orElseGet(() -> sinceParameter(tf)))
                                .build()
                )
                .retrieve()
                .bodyToMono(KrakenResponse.class)
                .map(it -> {
                    if (it.error().length > 0) {
                        logger.info(String.format("%s:%s", Messages.KRAKEN_FETCH_FAILED, symbol));
                    }

                    Map data = (Map) it.result();
                    return toCandlesArray((List) data.get(symbol));
                })
                .doOnError(e -> logger.info(e.getMessage()));
    }

    private long sinceParameter(Timeframe tf) {
        return switch (tf) {
            case H1 -> Instant.now().minus(3, ChronoUnit.HOURS).getEpochSecond();
            case H4 -> Instant.now().minus(12, ChronoUnit.HOURS).getEpochSecond();
            default -> Instant.now().minus(3, ChronoUnit.DAYS).getEpochSecond();
        };
    }

    private long sinceParameter(Timeframe tf, int amount) {
        if (Timeframe.D1.equals(tf)) {
            return Instant.now().minus(amount, ChronoUnit.DAYS).getEpochSecond();
        }
        return Instant.now().minus(amount, ChronoUnit.HOURS).getEpochSecond();
    }

    private int interval(Timeframe tf) {
        return switch (tf) {
            case H1 -> 60;
            case H4 -> 240;
            default -> 1440;
        };
    }


}
