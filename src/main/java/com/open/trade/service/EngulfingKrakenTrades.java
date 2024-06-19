package com.open.trade.service;

import com.open.trade.configuration.KrakenProps;
import com.open.trade.exchangecall.KrakenCall;
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
public class EngulfingKrakenTrades implements FetchNewTrades {
    private final Logger logger = LoggerFactory.getLogger(EngulfingKrakenTrades.class);

    private final OpportunityRepository repository;
    private final EngulfingCandleStrategy strategy;
    private final KrakenProps props;
    private final KrakenCall krakenCall;


    public EngulfingKrakenTrades(OpportunityRepository repository, EngulfingCandleStrategy strategy, KrakenProps props, KrakenCall krakenCall) {
        this.repository = repository;
        this.strategy = strategy;
        this.props = props;
        this.krakenCall = krakenCall;
    }

    @Override
    public void searchEntries(Speed speed) {
        this.props.symbols().forEach(symbol -> {
            saveInfo(symbol, krakenCall.engulfingCandles(symbol, speed), speed);
        });
    }

    @Transactional
    private void saveInfo(String symbol, Mono<Candle[]> values, Speed speed) {
        values.subscribe(
                it -> {
                    if (strategy.isEngulfing(it)) {
                        repository.findById(Opportunity.generateSimbolSpeed(symbol, speed))
                                .defaultIfEmpty(Opportunity.of(
                                        symbol,
                                        speed,
                                        false,
                                        0f,
                                        0f,
                                        0f,
                                        true,
                                        it[2].close(),
                                        it[2].close() * props.stop(),
                                        it[2].close() * props.profit()
                                ))
                                .flatMap(opportunity -> repository.save(new Opportunity(
                                        opportunity.symbolspeed(),
                                        opportunity.binanceengulfing(),
                                        opportunity.binanceprice(),
                                        opportunity.binancestop(),
                                        opportunity.binanceprofit(),
                                        true,
                                        it[2].close(),
                                        it[2].close() * props.stop(),
                                        it[2].close() * props.profit(),
                                        Instant.now().getEpochSecond(),
                                        opportunity.version()
                                ))).subscribe();
                    }
                }
        );
    }
}
