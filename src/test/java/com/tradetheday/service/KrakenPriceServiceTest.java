package com.tradetheday.service;

import com.tradetheday.exchangecall.KrakenCall;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class KrakenPriceServiceTest {

    @Mock
    KrakenCall krakenCall;


    @InjectMocks
    KrakenPriceService service;

    @Test
    void willFetchTheLatestPrice() {

        when(krakenCall.latestPrice(any())).thenReturn(Mono.just(2.3));

        Mono<Double> result = service.latestPrice("SOLUSD");

        StepVerifier.create(result)
                .thenConsumeWhile(it -> it.equals(2.3));

        verify(krakenCall, times(1)).latestPrice(any());
    }

}
