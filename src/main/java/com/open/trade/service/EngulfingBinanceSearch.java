package com.open.trade.service;

import com.open.trade.configuration.BinanceProps;
import com.open.trade.data.Candle;
import com.open.trade.exchangecall.BinanceCall;
import com.open.trade.model.Opportunity;
import com.open.trade.model.Speed;
import com.open.trade.repository.OpportunityRepository;
import com.open.trade.strategy.EngulfingCandleStrategy;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class EngulfingBinanceSearch implements FetchNewTrades {
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
    public void searchEntries(Speed speed) {
        this.props.symbols().forEach(symbol -> {
            saveInfo(symbol, binanceCall.engulfingCandles(symbol, speed), speed);
        });
    }


    private void saveInfo(String symbol, Mono<Candle[]> values, Speed speed) {
        values.subscribe(it -> {
            if (strategy.isEngulfing(it)) {
                Candle c1 = it[1];
                repository.save(Opportunity.of(
                        symbol,
                        speed,
                        true,
                        c1.close(),
                        c1.close() * props.stop(),
                        c1.close() * props.profit(),
                        speed,
                        false,
                        0f,
                        0f,
                        0f
                ));
            }
        });
    }
}
