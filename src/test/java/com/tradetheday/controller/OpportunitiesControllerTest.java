package com.tradetheday.controller;

import com.tradetheday.model.Opportunity;
import com.tradetheday.model.Timeframe;
import com.tradetheday.service.BinanceSearchService;
import com.tradetheday.service.KrakenSearchService;
import com.tradetheday.service.OpportunitiesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@WebFluxTest(OpportunitiesController.class)
public class OpportunitiesControllerTest {

    @MockBean
    OpportunitiesService opportunitiesService;

    @MockBean
    KrakenSearchService krakenSearch;

    @MockBean
    BinanceSearchService binanceSearch;

    @Autowired
    WebTestClient client;


    @Test
    void shouldGETSavedOpportunities() {

        given(opportunitiesService.findByTimeframe(any())).willReturn(
                Flux.just(Opportunity.of(
                        "BTCUSDT",
                        Timeframe.H1,
                        true,
                        true,
                        3000.00F,
                        3000.00F,
                        3000.00F,
                        false,
                        true,
                        0.0f,
                        0.0f,
                        0.0f
                ))
        );

        client.get()
                .uri("/tradetheday/crypto/opportunities/H1")
                .exchange()
                .expectStatus().is2xxSuccessful();

        verify(opportunitiesService, times(1)).findByTimeframe(any());
    }


}

