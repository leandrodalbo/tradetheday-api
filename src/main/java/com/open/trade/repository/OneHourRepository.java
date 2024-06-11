package com.open.trade.repository;

import com.open.trade.model.Onehour;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OneHourRepository extends CrudRepository<Onehour, String> {
}
