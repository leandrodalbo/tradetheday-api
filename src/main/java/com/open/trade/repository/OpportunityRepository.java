package com.open.trade.repository;

import com.open.trade.model.Opportunity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpportunityRepository extends ReactiveCrudRepository<Opportunity, String> {
}
