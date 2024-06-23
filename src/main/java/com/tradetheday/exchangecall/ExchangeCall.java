package com.tradetheday.exchangecall;

import com.tradetheday.exchanging.Candle;
import com.tradetheday.model.Timeframe;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

public abstract class ExchangeCall {

    protected final WebClient client;

    protected ExchangeCall(WebClient client) {
        this.client = client;
    }

    public static Candle[] toCandlesArray(List values) {
        if (values == null) {
            return new Candle[0];
        }

        Candle[] result = new Candle[values.size()];

        for (int i = 0; i < values.size(); i++) {
            List candle = (List) values.get(i);

            result[i] = Candle.of(
                    Float.parseFloat((String) candle.get(1)),
                    Float.parseFloat((String) candle.get(2)),
                    Float.parseFloat((String) candle.get(3)),
                    Float.parseFloat((String) candle.get(4)));

        }

        return result;
    }

    public abstract Mono<Candle[]> engulfingCandles(String symbol, Timeframe speed);

    public abstract Mono<Candle[]> MACandles(String symbol, Timeframe speed, int longestPeriod);
}
