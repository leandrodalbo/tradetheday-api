package com.open.trade.repository;

import com.open.trade.model.Opportunity;
import com.open.trade.model.Speed;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface OpportunityRepository extends ReactiveCrudRepository<Opportunity, Long> {

    Mono<Opportunity> findBySymbol(String symbol);

    @Query("""
            SELECT *
            FROM opportunity o
            WHERE (o.binanceengulfing is true AND o.binancespeed = :speed)
            OR (o.krakenengulfing is true  AND o.krakenspeed = :speed)
            """)
    Flux<Opportunity> findEngulfingBySpeed(Speed speed);

}
