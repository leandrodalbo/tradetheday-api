package com.open.trade.call;

import com.open.trade.data.Candle;
import org.springframework.web.reactive.function.client.WebClient;

public abstract class ExchangeCall {
    protected final WebClient client;

    public ExchangeCall(WebClient client) {
        this.client = client;
    }

    protected static Boolean isEngulfing(Candle firstCandle, Candle secondCandle) {
        return (firstCandle.open() > firstCandle.close()) && (secondCandle.open() < secondCandle.close()) && (secondCandle.close() > firstCandle.open()) && (secondCandle.open() < firstCandle.close());
    }

    public abstract void searchEngulfingEntries(String symbol, String interval);
}
