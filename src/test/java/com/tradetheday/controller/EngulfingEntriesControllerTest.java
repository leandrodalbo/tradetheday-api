package com.tradetheday.controller;

import com.tradetheday.model.Opportunity;
import com.tradetheday.model.Timeframe;
import com.tradetheday.service.OpportunitiesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import static org.mockito.BDDMockito.given;

@WebFluxTest(EngulfingEntriesController.class)
public class EngulfingEntriesControllerTest {

    @MockBean
    OpportunitiesService service;

    @Autowired
    WebTestClient client;


    @Test
    void shouldGETEntries() {

        given(service.findLatestEntries()).willReturn(
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
                .uri("/opentrade/crypto/engulfing/entries")
                .exchange()
                .expectStatus().is2xxSuccessful();
    }
}

