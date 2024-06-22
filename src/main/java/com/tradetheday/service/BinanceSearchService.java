package com.tradetheday.service;

import com.tradetheday.configuration.BinanceProps;
import com.tradetheday.exchangecall.BinanceCall;
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
public class BinanceSearchService implements OpportunitiesSearch {


    private final OpportunityRepository repository;
    private final EngulfingCandleStrategy engulfingStrategy;
    private final MACandleStrategy maStrategy;
    private final BinanceProps props;
    private final BinanceCall binanceCall;

    public BinanceSearchService(OpportunityRepository repository, EngulfingCandleStrategy engulfingStrategy, MACandleStrategy maStrategy, BinanceProps props, BinanceCall binanceCall) {
        this.repository = repository;
        this.engulfingStrategy = engulfingStrategy;
        this.maStrategy = maStrategy;
        this.props = props;
        this.binanceCall = binanceCall;
    }

    @Override
    public void searchEngulfingCandles(Timeframe tf) {
        this.props.symbols().forEach(symbol ->
                binanceCall.engulfingCandles(symbol, tf)
                        .filter(engulfingStrategy::isOn)
                        .subscribe(it -> saveInfo(symbol, it[it.length - 1], tf, true, false))
        );
    }

    @Override
    public void searchMACrossOver(Timeframe tf) {
        this.props.symbols().forEach(symbol ->
                binanceCall.MACandles(symbol, tf, props.longMA())
                        .filter(it -> maStrategy.isOn(new MACandleStrategy.MAStrategyData(it, props.shortMA(), props.longMA())))
                        .subscribe(it -> saveInfo(symbol, it[it.length - 1], tf, false, true))
        );
    }


    @Transactional
    private void saveInfo(String symbol, Candle latestCandle, Timeframe speed, boolean isEngulfing, boolean isMAcrossOver) {
        repository.findById(Opportunity.generateSimbolSpeed(symbol, speed))
                .defaultIfEmpty(
                        Opportunity.of(
                                symbol,
                                speed,
                                isEngulfing,
                                isMAcrossOver,
                                latestCandle.close(),
                                latestCandle.close() * props.stop(),
                                latestCandle.close() * props.profit(),
                                false,
                                false,
                                0f,
                                0f,
                                0f
                        )
                )
                .flatMap(opportunity ->
                        repository.save(new Opportunity(
                                opportunity.symbolspeed(),
                                isEngulfing || opportunity.binanceengulfing(),
                                isMAcrossOver || opportunity.binancema(),
                                latestCandle.close(),
                                latestCandle.close() * props.stop(),
                                latestCandle.close() * props.profit(),
                                opportunity.krakenengulfing(),
                                opportunity.krakenma(),
                                opportunity.krakenprice(),
                                opportunity.krakenstop(),
                                opportunity.krakenprofit(),
                                Instant.now().getEpochSecond(),
                                opportunity.version()
                        ))).subscribe();
    }


}
