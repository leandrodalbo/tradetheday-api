package com.open.trade.repository;

import com.open.trade.model.TradeOneHour;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OneHourRepository extends CrudRepository<TradeOneHour, String> {
}
