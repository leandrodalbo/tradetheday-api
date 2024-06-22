package com.tradetheday.service;

import com.tradetheday.model.Opportunity;
import com.tradetheday.model.Timeframe;
import com.tradetheday.repository.OpportunityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OpportunitiesServiceTest {

    @Mock
    OpportunityRepository repository;


    @InjectMocks
    OpportunitiesService service;

    @Test
    void findLatestEntries() {
        when(repository.findAll()).thenReturn(
                Flux.just(new Opportunity(
                                "BTCUSDT-H1",
                                true,
                                Instant.now().getEpochSecond(),
                                true,
                                Instant.now().getEpochSecond(),
                                3000.00F,
                                3000.00F,
                                3000.00F,
                                false,
                                Instant.now().getEpochSecond(),
                                true,
                                Instant.now().getEpochSecond(),
                                0.0f,
                                0.0f,
                                0.0f,
                                0
                        ),
                        new Opportunity(
                                Opportunity.generateId("BTCUSDT", Timeframe.H1),
                                true,
                                Instant.now().getEpochSecond(),
                                true,
                                Instant.now().getEpochSecond(),
                                3000.00F,
                                3000.00F,
                                3000.00F,
                                false,
                                Instant.now().getEpochSecond(),
                                true,
                                Instant.now().getEpochSecond(),
                                0.0f,
                                0.0f,
                                0.0f,
                                0
                        ))
        );

        Flux<Opportunity> result = service.findByTimeframe(Timeframe.H1);

        StepVerifier.create(result)
                .thenConsumeWhile(it -> it.binanceengulfingtime() > Instant.now().minus(1, ChronoUnit.HOURS).getEpochSecond());

        verify(repository, times(1)).findAll();
    }
}
