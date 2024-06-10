package com.tradeEntry.entries;

import com.tradeEntry.call.BinanceCall;
import com.tradeEntry.candlePattern.TwoCandleService;

public class EngulfingCandleService {

    private final TwoCandleService twoCandleService;
    private final BinanceCall binanceCall;

    public EngulfingCandleService(TwoCandleService twoCandleService, BinanceCall binanceCall) {
        this.twoCandleService = twoCandleService;
        this.binanceCall = binanceCall;
    }


}
