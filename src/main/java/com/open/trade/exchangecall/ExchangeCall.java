package com.open.trade.exchangecall;

import com.open.trade.data.Candle;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

public abstract class ExchangeCall {

    protected final WebClient client;

    protected ExchangeCall(WebClient client) {
        this.client = client;
    }

    protected static Candle[] candles(List values) {
        Candle[] result = new Candle[2];

        List valuesC0 = (List) values.get(0);
        List valuesC1 = (List) values.get(1);

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

        return result;
    }
}
