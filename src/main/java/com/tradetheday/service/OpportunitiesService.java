package com.tradetheday.service;

import com.tradetheday.model.Opportunity;
import com.tradetheday.model.Timeframe;
import com.tradetheday.repository.OpportunityRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;

@Service
public class OpportunitiesService {

    private final OpportunityRepository repository;

    public OpportunitiesService(OpportunityRepository repository) {
        this.repository = repository;
    }

    public Flux<Opportunity> findByTimeframe(Timeframe timeframe) {
        return repository.findAll()
                .filter(it ->
                        it.symbolspeed().contains(timeframe.toString())
                                &&
                                it.ondatetime() > Instant.now().minus(4, ChronoUnit.HOURS).getEpochSecond())
                .sort(Comparator.comparing(Opportunity::ondatetime).reversed());

    }
}
