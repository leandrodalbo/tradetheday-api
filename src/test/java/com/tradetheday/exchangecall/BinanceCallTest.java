package com.tradetheday.exchangecall;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tradetheday.configuration.WebClientProvider;
import com.tradetheday.exchanging.Candle;
import com.tradetheday.model.Timeframe;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BinanceCallTest {

    ObjectMapper mapper = new ObjectMapper();

    private MockWebServer mockWebServer;

    private BinanceCall binanceCall;

    @BeforeEach
    void setup() throws IOException {
        this.mockWebServer = new MockWebServer();
        this.mockWebServer.start();

        var webClient = WebClient.builder()
                .baseUrl(String.format("http://localhost:%s",
                        mockWebServer.getPort()))
                .build();

        WebClientProvider clientProvider = mock(WebClientProvider.class);
        when(clientProvider.binanceWebClient()).thenReturn(webClient);

        this.binanceCall = new BinanceCall(clientProvider);
    }

    @AfterEach
    void clean() throws IOException {
        this.mockWebServer.shutdown();
    }

    @Test
    void shouldFetchEngulfingCandles() throws JsonProcessingException {
        var mockResponse = new MockResponse()
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(mapper.writeValueAsString(List.of(List.of(
                                1591258320000L,
                                "9640.7",
                                "9642.4",
                                "9640.6",
                                "9642.0",
                                "206",
                                1591258379999L,
                                "2.13660389",
                                48,
                                "119",
                                "1.23424865",
                                "0"
                        ),
                        List.of(
                                1591258320000L,
                                "9640.7",
                                "9642.4",
                                "9640.6",
                                "9642.0",
                                "206",
                                1591258379999L,
                                "2.13660389",
                                48,
                                "119",
                                "1.23424865",
                                "0"
                        ),
                        List.of(
                                1591258320000L,
                                "9640.7",
                                "9642.4",
                                "9640.6",
                                "9642.0",
                                "206",
                                1591258379999L,
                                "2.13660389",
                                48,
                                "119",
                                "1.23424865",
                                "0"
                        )

                )));

        mockWebServer.enqueue(mockResponse);

        Mono candles = binanceCall.engulfingCandles("BTCUSD", Timeframe.H1);

        StepVerifier.create(candles)
                .expectNextMatches(it -> ((Candle[]) it).length == 3)
                .verifyComplete();

    }


    @Test
    void shouldFetchMaCandles() throws JsonProcessingException {
        var mockResponse = new MockResponse()
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(mapper.writeValueAsString(List.of(List.of(
                                1591258320000L,
                                "9640.7",
                                "9642.4",
                                "9640.6",
                                "9642.0",
                                "206",
                                1591258379999L,
                                "2.13660389",
                                48,
                                "119",
                                "1.23424865",
                                "0"
                        ),
                        List.of(
                                1591258320000L,
                                "9640.7",
                                "9642.4",
                                "9640.6",
                                "9642.0",
                                "206",
                                1591258379999L,
                                "2.13660389",
                                48,
                                "119",
                                "1.23424865",
                                "0"
                        ),
                        List.of(
                                1591258320000L,
                                "9640.7",
                                "9642.4",
                                "9640.6",
                                "9642.0",
                                "206",
                                1591258379999L,
                                "2.13660389",
                                48,
                                "119",
                                "1.23424865",
                                "0"
                        )

                )));

        mockWebServer.enqueue(mockResponse);

        Mono candles = binanceCall.MACandles("BTCUSD", Timeframe.H1, 3);

        StepVerifier.create(candles)
                .expectNextMatches(it -> ((Candle[]) it).length == 3)
                .verifyComplete();

    }

}
