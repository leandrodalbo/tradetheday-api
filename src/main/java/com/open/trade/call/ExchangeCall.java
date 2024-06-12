package com.open.trade.call;

import com.open.trade.data.Candle;
import com.open.trade.repository.OpportunityRepository;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

public abstract class ExchangeCall {

    protected final WebClient client;
    protected final OpportunityRepository repository;

    protected ExchangeCall(WebClient client, OpportunityRepository repository) {
        this.client = client;
        this.repository = repository;
    }


    protected static Boolean isEngulfing(Candle[] candles) {
        Candle prev = candles[0];
        Candle current = candles[1];
        return ((prev.open() > prev.close()) && (current.open() <= prev.close()) && current.close() >= prev.open());
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


    protected abstract void searchEngulfingEntries(String interval);

    protected abstract void saveInfo(String symbol, List values, String timeframe);
}
