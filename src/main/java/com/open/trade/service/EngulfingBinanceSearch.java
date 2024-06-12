package com.open.trade.service;

import com.open.trade.configuration.BinanceProps;
import com.open.trade.data.Candle;
import com.open.trade.exchangecall.BinanceCall;
import com.open.trade.model.Opportunity;
import com.open.trade.repository.OpportunityRepository;
import com.open.trade.strategy.EngulfingCandleStrategy;

public class EngulfingBinanceSearch implements SearchEntries {
    public final int TOTAL_CANDLES = 2;
    private final OpportunityRepository repository;
    private final EngulfingCandleStrategy strategy;
    private final BinanceProps props;
    private final BinanceCall binanceCall;

    public EngulfingBinanceSearch(OpportunityRepository repository, EngulfingCandleStrategy strategy, BinanceProps props, BinanceCall binanceCall) {
        this.repository = repository;
        this.strategy = strategy;
        this.props = props;
        this.binanceCall = binanceCall;
    }

    @Override
    public void searchEntries(String interval) {
        this.props.symbols().forEach(symbol -> {
            saveInfo(symbol, binanceCall.engulfingCandles(symbol, interval, TOTAL_CANDLES), interval);
        });
    }


    private void saveInfo(String symbol, Candle[] values, String timeframe) {
        if (strategy.isEngulfing(values)) {
            Candle c1 = values[1];
            repository.save(Opportunity.of(
                    symbol,
                    timeframe,
                    true,
                    c1.close(),
                    c1.close() * props.stop(),
                    c1.close() * props.profit(),
                    0L,
                    false,
                    0f,
                    0f,
                    0f
            ));
        }
    }
}
