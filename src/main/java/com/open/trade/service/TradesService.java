package com.open.trade.service;

import com.open.trade.model.Trade;
import com.open.trade.model.TradeStatus;
import com.open.trade.repository.TradeRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class TradesService {

    private final TradeRepository repository;

    public TradesService(TradeRepository repository) {
        this.repository = repository;
    }

    public Flux<Trade> findByStatus(TradeStatus status) {
        return repository.findByStatus(status);
    }
}
