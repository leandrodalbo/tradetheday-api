package com.tradetheday.controller;

import com.tradetheday.exchanging.kraken.KrakenMarketBuy;
import com.tradetheday.exchanging.kraken.KrakenStopLoss;
import com.tradetheday.service.KrakenOrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@WebFluxTest(KrakenOrderController.class)
public class KrakenOrderControllerTest {

    @MockBean
    KrakenOrderService service;

    @Autowired
    WebTestClient client;


    @Test
    void shouldEnterWithSymbol() {

        given(service.dareToEnter(any())).willReturn(
                Mono.just("SUCCESS"));

        client.post()
                .uri("/tradetheday/crypto/kraken/enter")
                .bodyValue(new KrakenMarketBuy("BTCUSD", 0.2))
                .exchange()
                .expectStatus().is2xxSuccessful();
    }

    @Test
    void shouldSetStopLossForSymbol() {

        given(service.setStopLoss(any())).willReturn(
                Mono.just("SUCCESS"));

        client.post()
                .uri("/tradetheday/crypto/kraken/stop")
                .bodyValue(new KrakenStopLoss("BTCUSD", 0.2, 30000.0))
                .exchange()
                .expectStatus().is2xxSuccessful();
    }
}

