package com.open.trade.service;

import com.open.trade.model.Trade;
import com.open.trade.model.TradeStatus;
import com.open.trade.repository.TradeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TradesServiceTest {

    @Mock
    TradeRepository repository;


    @InjectMocks
    TradesService service;


    @Test
    void willDelegateSearchToRepository() {
        when(repository.findByTraderesultAndTradestatusAndOndatetimeGreaterThan(
                any(),
                any(),
                anyLong()
        )).thenReturn(
                Flux.just(
                        Trade.of("BTCUSD", 0.01, 55000.3, 54.033, 50.002, TradeStatus.OPEN, true),
                        Trade.of("BTCUSD", 0.01, 55000.3, 54.033, 50.002, TradeStatus.OPEN, false)
                )
        );

        Flux<Trade> result = service.findTrades(Optional.empty(), Optional.empty(), Optional.empty());

        StepVerifier.create(result)
                .thenConsumeWhile(it -> it.tradestatus().equals(TradeStatus.OPEN))
                .verifyComplete();

        verify(repository, times(1)).findByTraderesultAndTradestatusAndOndatetimeGreaterThan(any(), any(), anyLong());
    }

}
