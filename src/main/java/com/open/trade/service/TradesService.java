package com.open.trade.service;

import com.open.trade.model.Trade;
import com.open.trade.model.TradeResult;
import com.open.trade.model.TradeStatus;
import com.open.trade.repository.TradeRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.Optional;

@Service
public class TradesService {

    private final TradeRepository repository;

    public TradesService(TradeRepository repository) {
        this.repository = repository;
    }

    public Flux<Trade> findTrades(Optional<TradeStatus> status, Optional<TradeResult> result, Optional<Boolean> today) {
        return repository.findAll()
                .filter(it ->
                        tradeFilter(it, result, status, today)
                )
                .sort(Comparator.comparing(Trade::ondatetime).reversed());
    }

    public boolean tradeFilter(Trade trade, Optional<TradeResult> result, Optional<TradeStatus> status, Optional<Boolean> isToday) {
        if (status.isPresent() && result.isPresent() && isToday.isPresent()) {
            return (trade.tradestatus().equals(status.get()) && trade.traderesult().equals(result.get()) && trade.ondatetime() > Instant.now().minus(1, ChronoUnit.DAYS).getEpochSecond());
        }

        if (status.isPresent() && result.isPresent() && isToday.isEmpty()) {
            return (trade.tradestatus().equals(status.get()) && trade.traderesult().equals(result.get()));
        }

        if (status.isPresent() && result.isEmpty() && isToday.isPresent()) {
            return (trade.tradestatus().equals(status.get()) && trade.ondatetime() > Instant.now().minus(1, ChronoUnit.DAYS).getEpochSecond());
        }

        if (status.isEmpty() && result.isPresent() && isToday.isPresent()) {
            return (trade.traderesult().equals(result.get()) && trade.ondatetime() > Instant.now().minus(1, ChronoUnit.DAYS).getEpochSecond());
        }

        if (status.isEmpty() && result.isEmpty() && isToday.isPresent()) {
            return (trade.ondatetime() > Instant.now().minus(1, ChronoUnit.DAYS).getEpochSecond());
        }

        if (status.isEmpty() && result.isPresent() && isToday.isEmpty()) {
            return (trade.traderesult().equals(result.get()));
        }

        if (status.isPresent() && result.isEmpty() && isToday.isEmpty()) {
            return (trade.tradestatus().equals(status.get()));
        }

        return true;

    }
}
