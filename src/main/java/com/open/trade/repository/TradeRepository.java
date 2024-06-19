package com.open.trade.repository;

import com.open.trade.model.Trade;
import com.open.trade.model.TradeResult;
import com.open.trade.model.TradeStatus;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface TradeRepository extends ReactiveCrudRepository<Trade, Long> {
    Flux<Trade> findByTradestatus(TradeStatus tradestatus);

    Flux<Trade> findByTraderesult(TradeResult traderesult);

    Flux<Trade> findByTraderesultAndTradestatusAndOndatetimeGreaterThan(TradeResult traderesult, TradeStatus tradestatus, long ondatetime);
}
