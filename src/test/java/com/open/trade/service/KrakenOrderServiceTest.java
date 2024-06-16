package com.open.trade.service;

import com.open.trade.model.Opportunity;
import com.open.trade.model.Speed;
import com.open.trade.repository.OpportunityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class KrakenOrderServiceTest {

    @Mock
    OpportunityRepository repository;


    @InjectMocks
    OpportunitiesService service;

    @Test
    void findBySpeed() {
        when(repository.findEngulfingBySpeed(any())).thenReturn(
                Flux.just(Opportunity.of(
                                "BTCUSDT",
                                Speed.HIGH,
                                true,
                                3000.00F,
                                3000.00F,
                                3000.00F,
                                Speed.HIGH,
                                false,
                                0.0f,
                                0.0f,
                                0.0f
                        ),
                        new Opportunity(23123L,
                                "BTCUSDT",
                                Speed.HIGH,
                                true,
                                3000.00F,
                                3000.00F,
                                3000.00F,
                                Speed.LOW,
                                false,
                                0.0f,
                                0.0f,
                                0.0f,
                                Instant.now().minus(1, ChronoUnit.DAYS).getEpochSecond(),
                                0

                        ))
        );

        Flux<Opportunity> result = service.findTodayEngulfingBySpeed(Speed.HIGH);

        StepVerifier.create(result)
                .thenConsumeWhile(it -> it.binancespeed().equals(Speed.HIGH) && it.krakenspeed().equals(Speed.HIGH) && it.ondatetime() > Instant.now().minus(1, ChronoUnit.DAYS).getEpochSecond());

        verify(repository, times(1)).findEngulfingBySpeed(any());
    }
}
