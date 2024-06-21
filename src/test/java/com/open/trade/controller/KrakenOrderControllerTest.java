package com.open.trade.controller;

import com.open.trade.exchanging.kraken.KrakenMarketBuy;
import com.open.trade.service.KrakenOrderService;
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
    void shouldGETEngulfingResultsBySpeed() {

        given(service.newTrade(any())).willReturn(
                Mono.just(Trade.of("BTCUSD",
                        0.2,
                        65000.3,
                        65000.0,
                        62000.3,
                        TradeStatus.OPEN,
                        true
                ))
        );

        client.post()
                .uri("/opentrade/crypto/kraken/neworder")
                .bodyValue(new KrakenMarketBuy("BTCUSD", 0.2, 65000.3, 65000.0, 62000.3))
                .exchange()
                .expectStatus().is2xxSuccessful();
    }
}

