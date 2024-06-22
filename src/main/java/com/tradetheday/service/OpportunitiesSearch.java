package com.tradetheday.service;

import com.tradetheday.model.Opportunity;
import com.tradetheday.model.Timeframe;
import com.tradetheday.repository.OpportunityRepository;
import com.tradetheday.strategy.EngulfingCandleStrategy;
import com.tradetheday.strategy.MACandleStrategy;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public abstract class OpportunitiesSearch {

    protected final EngulfingCandleStrategy engulfingStrategy;
    protected final MACandleStrategy maStrategy;
    private final OpportunityRepository repository;

    public OpportunitiesSearch(OpportunityRepository repository, EngulfingCandleStrategy engulfingStrategy, MACandleStrategy maStrategy) {
        this.repository = repository;
        this.engulfingStrategy = engulfingStrategy;
        this.maStrategy = maStrategy;
    }

    protected abstract void searchEngulfingCandles(Timeframe interval);

    protected abstract void searchMACrossOver(Timeframe interval);

    @Transactional
    protected void saveInfo(SavingData data) {
        repository.findById(Opportunity.generateId(data.symbol, data.timeframe))
                .defaultIfEmpty(
                        Opportunity.of(
                                data.symbol(),
                                data.timeframe(),
                                data.updateBinance(),
                                data.updateKraken(),
                                Optional.empty(),
                                data.isEngulfing(),
                                data.engulfingCandleTime(),
                                data.isMACrossover(),
                                data.MACrossoverTime(),
                                data.price(),
                                data.stop(),
                                data.profit()
                        )
                )
                .flatMap(opportunity ->
                        repository.save(Opportunity.of(
                                data.symbol(),
                                data.timeframe(),
                                data.updateBinance(),
                                data.updateKraken(),
                                Optional.of(opportunity),
                                data.isEngulfing(),
                                data.engulfingCandleTime(),
                                data.isMACrossover(),
                                data.MACrossoverTime(),
                                data.price(),
                                data.stop(),
                                data.profit()
                        ))).subscribe();
    }


    protected record SavingData(
            String symbol,
            Timeframe timeframe,
            boolean updateBinance,
            boolean updateKraken,
            Optional<Boolean> isEngulfing,
            Optional<Long> engulfingCandleTime,
            Optional<Boolean> isMACrossover,
            Optional<Long> MACrossoverTime,
            Optional<Float> price,
            Optional<Float> stop,
            Optional<Float> profit) {
    }
}
