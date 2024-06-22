package com.tradetheday.controller;

import com.tradetheday.model.Opportunity;
import com.tradetheday.service.BinanceSearchService;
import com.tradetheday.service.KrakenSearchService;
import com.tradetheday.service.OpportunitiesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.time.Instant;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

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
    void shouldSearchNewBinanceEngulfingCandles() {

        doNothing().when(binanceSearch).searchEngulfingCandles(any());


        client.get()
                .uri("/tradetheday/crypto/search/binance/engulfing")
                .exchange()
                .expectStatus().is2xxSuccessful();

        verify(binanceSearch, times(3)).searchEngulfingCandles(any());
    }

    @Test
    void shouldSearchNewKrakenEngulfingCandles() {

        doNothing().when(krakenSearch).searchEngulfingCandles(any());


        client.get()
                .uri("/tradetheday/crypto/search/kraken/engulfing")
                .exchange()
                .expectStatus().is2xxSuccessful();

        verify(krakenSearch, times(3)).searchEngulfingCandles(any());
    }


    @Test
    void shouldSearchNewBinanceMAS() {

        doNothing().when(binanceSearch).searchMACrossOver(any());


        client.get()
                .uri("/tradetheday/crypto/search/binance/ma")
                .exchange()
                .expectStatus().is2xxSuccessful();

        verify(binanceSearch, times(3)).searchMACrossOver(any());
    }

    @Test
    void shouldSearchNewKrakenMAs() {

        doNothing().when(krakenSearch).searchMACrossOver(any());


        client.get()
                .uri("/tradetheday/crypto/search/kraken/ma")
                .exchange()
                .expectStatus().is2xxSuccessful();

        verify(krakenSearch, times(3)).searchMACrossOver(any());
    }

    @Test
    void shouldGETSavedOpportunities() {

        given(opportunitiesService.findByTimeframe(any())).willReturn(
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
                ))
        );

        client.get()
                .uri("/tradetheday/crypto/opportunities/H1")
                .exchange()
                .expectStatus().is2xxSuccessful();

        verify(opportunitiesService, times(1)).findByTimeframe(any());
    }


}

