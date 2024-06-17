package com.open.trade.controller;

import com.open.trade.model.Trade;
import com.open.trade.model.TradeStatus;
import com.open.trade.service.TradesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@WebFluxTest(TradesController.class)
public class TradesControllerTest {

    @MockBean
    TradesService service;

    @Autowired
    WebTestClient client;


    @Test
    void shouldGETEngulfingResultsBySpeed() {

        given(service.findByStatus(any())).willReturn(
                Flux.just(
                        Trade.of("SOLUSDT", 2, 57.3, 55, TradeStatus.OPEN)
                )
        );

        client.get()
                .uri("/opentrade/crypto/trades/OPEN")
                .exchange()
                .expectStatus().is2xxSuccessful();


    }
}

