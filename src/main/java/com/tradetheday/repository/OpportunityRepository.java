package com.tradetheday.repository;

import com.tradetheday.model.Opportunity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpportunityRepository extends ReactiveCrudRepository<Opportunity, String> {
}
