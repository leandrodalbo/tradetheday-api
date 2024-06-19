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
        return repository.findByTraderesultAndTradestatusAndOndatetimeGreaterThan(
                result.isPresent() ? result.get() : null,
                status.isPresent() ? status.get() : TradeStatus.OPEN,
                today.isPresent() ? Instant.now().minus(1, ChronoUnit.DAYS).getEpochSecond() :
                        Instant.now().minus(15, ChronoUnit.DAYS).getEpochSecond()

        ).sort(Comparator.comparing(Trade::ondatetime).reversed());
    }
}
