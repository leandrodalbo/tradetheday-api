package com.open.trade.repository;

import com.open.trade.model.Opportunity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpportunityRepository extends CrudRepository<Opportunity, Long> {
}
