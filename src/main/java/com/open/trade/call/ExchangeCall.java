package com.open.trade.call;

import com.open.trade.data.Candle;

public abstract class ExchangeCall {

    protected static Boolean isEngulfing(Candle firstCandle, Candle secondCandle) {
        return (firstCandle.open() > firstCandle.close()) && (secondCandle.open() < secondCandle.close()) && (secondCandle.close() > firstCandle.open()) && (secondCandle.open() < firstCandle.close());
    }

    public abstract void searchEngulfingEntries(String interval);
}
