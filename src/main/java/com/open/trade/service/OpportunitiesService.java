package com.open.trade.service;

import com.open.trade.model.Opportunity;
import com.open.trade.repository.OpportunityRepository;
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

    public Flux<Opportunity> findLatestEntries() {
        return repository.findAll()
                .filter(it ->
                        it.ondatetime() > Instant.now().minus(1, ChronoUnit.DAYS).getEpochSecond());
    }
}
