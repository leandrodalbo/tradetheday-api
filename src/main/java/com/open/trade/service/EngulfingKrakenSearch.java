package com.open.trade.service;

import com.open.trade.configuration.KrakenProps;
import com.open.trade.data.Candle;
import com.open.trade.exchangecall.KrakenCall;
import com.open.trade.model.Opportunity;
import com.open.trade.repository.OpportunityRepository;
import com.open.trade.strategy.EngulfingCandleStrategy;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class EngulfingKrakenSearch implements SearchEntries {
    private final OpportunityRepository repository;
    private final EngulfingCandleStrategy strategy;
    private final KrakenProps props;
    private final KrakenCall krakenCall;

    public EngulfingKrakenSearch(OpportunityRepository repository, EngulfingCandleStrategy strategy, KrakenProps props, KrakenCall krakenCall) {
        this.repository = repository;
        this.strategy = strategy;
        this.props = props;
        this.krakenCall = krakenCall;
    }

    @Override
    public void searchEntries(String interval) {
        this.props.symbols().forEach(symbol -> {
            saveInfo(symbol, krakenCall.fetchData(symbol, Integer.parseInt(interval), sinceParameter(interval)), interval);
        });
    }

    private void saveInfo(String symbol, Candle[] values, String timeframe) {
        if (strategy.isEngulfing(values)) {
            Candle c1 = values[1];
            repository.save(Opportunity.of(
                    symbol,
                    "0",
                    false,
                    0f,
                    0f,
                    0f,
                    Long.parseLong(timeframe),
                    true,
                    c1.close(),
                    c1.close() * props.stop(),
                    c1.close() * props.profit()

            ));
        }
    }


    private long sinceParameter(String minutes) {
        return switch (Integer.parseInt(minutes)) {
            case 60 -> Instant.now().minus(2, ChronoUnit.HOURS).getEpochSecond();
            case 240 -> Instant.now().minus(8, ChronoUnit.HOURS).getEpochSecond();
            default -> Instant.now().minus(2, ChronoUnit.DAYS).getEpochSecond();
        };
    }
}
