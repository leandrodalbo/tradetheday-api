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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TradesServiceTest {

    @Mock
    TradeRepository repository;


    @InjectMocks
    TradesService service;

    @Test
    void findByStatus() {
        when(repository.findByStatus(any())).thenReturn(
                Flux.just(
                        Trade.of("BTCUSD", 0.01, 54.033, 50.002, TradeStatus.OPEN),
                        Trade.of("BTCUSD", 0.01, 54.033, 50.002, TradeStatus.CLOSED)
                )
        );

        Flux<Trade> result = service.findByStatus(TradeStatus.OPEN);

        StepVerifier.create(result)
                .thenConsumeWhile(it -> it.status().equals(TradeStatus.OPEN));

        verify(repository, times(1)).findByStatus(any());
    }
}
