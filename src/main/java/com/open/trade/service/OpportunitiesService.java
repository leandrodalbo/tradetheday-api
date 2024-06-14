package com.open.trade.service;

import com.open.trade.model.Opportunity;
import com.open.trade.model.Speed;
import com.open.trade.repository.OpportunityRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class OpportunitiesService {

    private final OpportunityRepository repository;

    public OpportunitiesService(OpportunityRepository repository) {
        this.repository = repository;
    }

    public Flux<Opportunity> findEngulfingBySpeed(Speed speed) {
        return repository.findEngulfingBySpeed(speed);
    }
}
