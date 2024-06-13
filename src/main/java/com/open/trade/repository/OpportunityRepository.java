package com.open.trade.repository;

import com.open.trade.model.Opportunity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface OpportunityRepository extends ReactiveCrudRepository<Opportunity, Long> {

    Mono<Opportunity> findBySymbol(String symbol);

}
