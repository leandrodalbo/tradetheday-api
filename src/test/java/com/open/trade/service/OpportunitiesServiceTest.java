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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OpportunitiesServiceTest {

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
                ))
        );

        Flux<Opportunity> result = service.findEngulfingBySpeed(Speed.HIGH);

        StepVerifier.create(result)
                .thenConsumeWhile(it -> it.binancespeed().equals(Speed.HIGH) && it.krakenspeed().equals(Speed.HIGH));

        verify(repository, times(1)).findEngulfingBySpeed(any());
    }
}
