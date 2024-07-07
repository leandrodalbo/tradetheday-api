package com.tradetheday.service;

import com.tradetheday.configuration.ExchangeProps;
import com.tradetheday.configuration.KrakenProps;
import com.tradetheday.exchangecall.KrakenCall;
import com.tradetheday.model.Timeframe;
import com.tradetheday.repository.OpportunityRepository;
import com.tradetheday.strategy.EngulfingCandleStrategy;
import com.tradetheday.strategy.MACandleStrategy;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class KrakenSearchService extends OpportunitiesSearch {


    private final KrakenCall krakenCall;


    public KrakenSearchService(OpportunityRepository repository, EngulfingCandleStrategy engulfingStrategy, MACandleStrategy maStrategy, KrakenCall krakenCall) {
        super(repository, engulfingStrategy, maStrategy);
        this.krakenCall = krakenCall;
    }

    @Override
    public void searchEngulfingCandles(String symbol, Timeframe tf, ExchangeProps props) {
        krakenCall.engulfingCandles(symbol, tf)
                .filter(engulfingStrategy::isOn)
                .subscribe(it -> saveInfo(new SavingData(
                                symbol,
                                tf,
                                false,
                                true,
                                Optional.of(Boolean.TRUE),
                                Optional.of(Instant.now().getEpochSecond()),
                                Optional.empty(),
                                Optional.empty(),
                                Optional.of(it[it.length - 1].close()),
                                Optional.of(it[it.length - 1].close() * props.stop()),
                                Optional.of(it[it.length - 1].close() * props.profit())
                        )
                ));

    }

    @Override
    public void searchMACrossOver(String symbol, Timeframe tf, ExchangeProps krakenProps) {
        KrakenProps props = (KrakenProps) krakenProps;
        krakenCall.MACandles(symbol, tf, props.longma() + props.extracandles())
                .filter(it -> maStrategy.isOn(new MACandleStrategy.MAStrategyData(it, props.shortma(), props.longma(), props.extracandles())))
                .subscribe(it -> saveInfo(new SavingData(
                        symbol,
                        tf,
                        false,
                        true,
                        Optional.empty(),
                        Optional.empty(),
                        Optional.of(Boolean.TRUE),
                        Optional.of(Instant.now().getEpochSecond()),
                        Optional.of(it[it.length - 1].close()),
                        Optional.of(it[it.length - 1].close() * props.stop()),
                        Optional.of(it[it.length - 1].close() * props.profit())
                )));
    }
}
