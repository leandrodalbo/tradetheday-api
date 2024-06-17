package com.open.trade.exchangecall;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.open.trade.configuration.WebClientProvider;
import com.open.trade.data.Candle;
import com.open.trade.data.kraken.KrakenOrderPost;
import com.open.trade.data.kraken.KrakenOrderPostBody;
import com.open.trade.data.kraken.KrakenPostResult;
import com.open.trade.exchangecall.exchange.KrakenResponse;
import com.open.trade.model.Speed;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class KrakenCallTest {

    ObjectMapper mapper = new ObjectMapper();

    private MockWebServer mockWebServer;

    @InjectMocks
    private KrakenCall krakenCall;

    @BeforeEach
    void setup() throws IOException {
        this.mockWebServer = new MockWebServer();
        this.mockWebServer.start();

        var webClient = WebClient.builder()
                .baseUrl(String.format("http://localhost:%s",
                        mockWebServer.getPort()))
                .build();

        WebClientProvider clientProvider = mock(WebClientProvider.class);
        when(clientProvider.krakenWebClient()).thenReturn(webClient);

        this.krakenCall = new KrakenCall(clientProvider);
    }

    @AfterEach
    void clean() throws IOException {
        this.mockWebServer.shutdown();
    }

    @Test
    void shouldFetchEngulfingCandles() throws JsonProcessingException {
        var mockResponse = new MockResponse()
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(mapper.writeValueAsString(
                        new KrakenResponse(new String[]{},
                                Map.of(
                                        "BTCUSD",
                                        List.of(
                                                List.of(
                                                        1688671200,
                                                        "30306.1",
                                                        "30306.2",
                                                        "30305.7",
                                                        "30305.7",
                                                        "30306.1",
                                                        "3.39243896",
                                                        23),
                                                List.of(
                                                        1688671200,
                                                        "30306.1",
                                                        "30306.2",
                                                        "30305.7",
                                                        "30305.7",
                                                        "30306.1",
                                                        "3.39243896",
                                                        23),
                                                List.of(
                                                        1688671200,
                                                        "30306.1",
                                                        "30306.2",
                                                        "30305.7",
                                                        "30305.7",
                                                        "30306.1",
                                                        "3.39243896",
                                                        23)
                                        )
                                )
                        )
                ));

        mockWebServer.enqueue(mockResponse);

        Mono candles = krakenCall.engulfingCandles("BTCUSD", Speed.HIGH);

        StepVerifier.create(candles)
                .expectNextMatches(it -> ((Candle[]) it).length == 3)
                .verifyComplete();
    }

    @Test
    void shouldPostANewOrder() throws JsonProcessingException {
        var mockResponse = new MockResponse()
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(mapper.writeValueAsString(
                        new KrakenResponse(new String[0], null)
                ));

        mockWebServer.enqueue(mockResponse);

        Mono<KrakenPostResult> result = krakenCall.postOrder(new KrakenOrderPost("abdq4sa22", "sabdsbq4dafg", "/AddOrder", new KrakenOrderPostBody(
                System.currentTimeMillis(),
                "market",
                "buy",
                0.2,
                "SOLUSD"
        )));

        StepVerifier.create(result)
                .expectNextMatches(KrakenPostResult::success)
                .verifyComplete();
    }

    @Test
    void shouldHandleItWhenPostingAnOrderFails() throws JsonProcessingException {
        var mockResponse = new MockResponse()
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(mapper.writeValueAsString(
                        new KrakenResponse(new String[]{"failed"}, null)
                ));

        mockWebServer.enqueue(mockResponse);

        Mono<KrakenPostResult> result = krakenCall.postOrder(new KrakenOrderPost("abdq4sa22", "sabdsbq4dafg", "/AddOrder", new KrakenOrderPostBody(
                System.currentTimeMillis(),
                "market",
                "buy",
                0.2,
                "SOLUSD"
        )));

        StepVerifier.create(result)
                .expectNextMatches(it -> !it.success())
                .verifyComplete();
    }

    @Test
    void shouldFetchTheLatestPrice() throws JsonProcessingException {
        var mockResponse = new MockResponse()
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(mapper.writeValueAsString(
                        new KrakenResponse(new String[0], Map.of(
                                "BTCUSD",
                                Map.of(
                                        "c", new String[]{"142.750000", "0.44623432"}
                                ))
                        )));

        mockWebServer.enqueue(mockResponse);

        Mono<Object> result = krakenCall.latestPrice("BTCUSD");

        StepVerifier.create(result)
                .expectNextMatches(it -> it.equals(142.750000))
                .verifyComplete();
    }


    @Test
    void shouldHandleTickerError() throws JsonProcessingException {
        var mockResponse = new MockResponse()
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(mapper.writeValueAsString(
                        new KrakenResponse(new String[]{"failed"}, null)
                ));

        mockWebServer.enqueue(mockResponse);

        Mono<Object> result = krakenCall.latestPrice("BTCUSD");

        StepVerifier.create(result)
                .expectError();
    }

}
