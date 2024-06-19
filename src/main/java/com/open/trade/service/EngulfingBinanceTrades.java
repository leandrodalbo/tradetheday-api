package com.open.trade.service;

import com.open.trade.configuration.BinanceProps;
import com.open.trade.exchangecall.BinanceCall;
import com.open.trade.exchanging.Candle;
import com.open.trade.model.Opportunity;
import com.open.trade.model.Speed;
import com.open.trade.repository.OpportunityRepository;
import com.open.trade.strategy.EngulfingCandleStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Service
public class EngulfingBinanceTrades implements FetchNewTrades {

    private final Logger logger = LoggerFactory.getLogger(EngulfingBinanceTrades.class);

    private final OpportunityRepository repository;
    private final EngulfingCandleStrategy strategy;
    private final BinanceProps props;
    private final BinanceCall binanceCall;

    public EngulfingBinanceTrades(OpportunityRepository repository, EngulfingCandleStrategy strategy, BinanceProps props, BinanceCall binanceCall) {
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

    @Transactional
    private void saveInfo(String symbol, Mono<Candle[]> values, Speed speed) {
        values.subscribe(it -> {
            if (strategy.isEngulfing(it)) {
                repository.findById(Opportunity.generateSimbolSpeed(symbol, speed))
                        .defaultIfEmpty(
                                Opportunity.of(
                                        symbol,
                                        speed,
                                        true,
                                        it[2].close(),
                                        it[2].close() * props.stop(),
                                        it[2].close() * props.profit(),
                                        false,
                                        0f,
                                        0f,
                                        0f
                                )
                        )
                        .flatMap(opportunity ->
                                repository.save(new Opportunity(
                                        opportunity.symbolspeed(),
                                        true,
                                        it[2].close(),
                                        it[2].close() * props.stop(),
                                        it[2].close() * props.profit(),
                                        opportunity.krakenengulfing(),
                                        opportunity.krakenprice(),
                                        opportunity.krakenstop(),
                                        opportunity.krakenprofit(),
                                        Instant.now().getEpochSecond(),
                                        opportunity.version()
                                ))).subscribe();
            }
        });
    }
}
