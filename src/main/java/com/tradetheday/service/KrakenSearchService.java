package com.tradetheday.service;

import com.tradetheday.configuration.KrakenProps;
import com.tradetheday.exchangecall.KrakenCall;
import com.tradetheday.exchanging.Candle;
import com.tradetheday.model.Opportunity;
import com.tradetheday.model.Timeframe;
import com.tradetheday.repository.OpportunityRepository;
import com.tradetheday.strategy.EngulfingCandleStrategy;
import com.tradetheday.strategy.MACandleStrategy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class KrakenSearchService implements OpportunitiesSearch {

    private final OpportunityRepository repository;
    private final EngulfingCandleStrategy engulfingStrategy;
    private final MACandleStrategy maStrategy;
    private final KrakenProps props;
    private final KrakenCall krakenCall;


    public KrakenSearchService(OpportunityRepository repository, EngulfingCandleStrategy engulfingStrategy, MACandleStrategy maStrategy, KrakenProps props, KrakenCall krakenCall) {
        this.repository = repository;
        this.engulfingStrategy = engulfingStrategy;
        this.maStrategy = maStrategy;
        this.props = props;
        this.krakenCall = krakenCall;
    }

    @Override
    @Transactional
    public void searchEngulfingCandles(Timeframe tf) {
        this.props.symbols().forEach(symbol ->
                krakenCall.engulfingCandles(symbol, tf)
                        .filter(engulfingStrategy::isOn)
                        .subscribe(it -> saveInfo(symbol, it[it.length - 1], tf, true, false))
        );
    }

    @Override
    @Transactional
    public void searchMACrossOver(Timeframe tf) {
        this.props.symbols().forEach(symbol ->
                krakenCall.MACandles(symbol, tf, props.longMA())
                        .filter(it -> maStrategy.isOn(new MACandleStrategy.MAStrategyData(it, props.shortMA(), props.longMA())))
                        .subscribe(it -> saveInfo(symbol, it[it.length - 1], tf, false, true))
        );
    }


    private void saveInfo(String symbol, Candle latestCandle, Timeframe speed, boolean isEngulfing, boolean isMACrossover) {
        repository.findById(Opportunity.generateSimbolSpeed(symbol, speed))
                .defaultIfEmpty(Opportunity.of(
                        symbol,
                        speed,
                        false,
                        false,
                        0f,
                        0f,
                        0f,
                        isEngulfing,
                        isMACrossover,
                        latestCandle.close(),
                        latestCandle.close() * props.stop(),
                        latestCandle.close() * props.profit()
                ))
                .flatMap(opportunity -> repository.save(new Opportunity(
                        opportunity.symbolspeed(),
                        opportunity.binanceengulfing(),
                        opportunity.binancema(),
                        opportunity.binanceprice(),
                        opportunity.binancestop(),
                        opportunity.binanceprofit(),
                        isEngulfing || opportunity.krakenengulfing(),
                        isMACrossover || opportunity.krakenma(),
                        latestCandle.close(),
                        latestCandle.close() * props.stop(),
                        latestCandle.close() * props.profit(),
                        Instant.now().getEpochSecond(),
                        opportunity.version()
                ))).subscribe();


    }
}
