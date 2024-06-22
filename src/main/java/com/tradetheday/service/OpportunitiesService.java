package com.tradetheday.service;

import com.tradetheday.model.Opportunity;
import com.tradetheday.model.Timeframe;
import com.tradetheday.repository.OpportunityRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class OpportunitiesService {

    private final OpportunityRepository repository;

    public OpportunitiesService(OpportunityRepository repository) {
        this.repository = repository;
    }

    public Flux<Opportunity> findByTimeframe(Timeframe timeframe) {
        long time = Instant.now().minus(2, ChronoUnit.HOURS).getEpochSecond();
        return repository.findAll()
                .filter(it ->
                        it.binanceengulfingtime() >= time ||
                                it.binancematime() >= time ||
                                it.krakenengulfingtime() >= time ||
                                it.krakenmatime() >= time);
    }
}
