package com.open.trade.exchangecall;

import com.open.trade.exchanging.Candle;
import com.open.trade.model.Speed;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

public abstract class ExchangeCall {

    protected final WebClient client;

    protected ExchangeCall(WebClient client) {
        this.client = client;
    }

    public static Candle[] engulfingToArray(List values) {
        Candle[] result = new Candle[3];

        if (values == null || values.size() < 3)
            return result;

        List valuesC0 = (List) values.get(0);
        List valuesC1 = (List) values.get(1);
        List valuesC2 = (List) values.get(2);

        result[0] = Candle.of(
                Float.parseFloat((String) valuesC0.get(1)),
                Float.parseFloat((String) valuesC0.get(2)),
                Float.parseFloat((String) valuesC0.get(3)),
                Float.parseFloat((String) valuesC0.get(4)));

        result[1] = Candle.of(
                Float.parseFloat((String) valuesC1.get(1)),
                Float.parseFloat((String) valuesC1.get(2)),
                Float.parseFloat((String) valuesC1.get(3)),
                Float.parseFloat((String) valuesC1.get(4)));

        result[2] = Candle.of(
                Float.parseFloat((String) valuesC2.get(1)),
                Float.parseFloat((String) valuesC2.get(2)),
                Float.parseFloat((String) valuesC2.get(3)),
                Float.parseFloat((String) valuesC2.get(4)));

        return result;
    }

    public abstract Mono<Candle[]> engulfingCandles(String symbol, Speed speed);
}
