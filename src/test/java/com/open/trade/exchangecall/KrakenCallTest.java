package com.open.trade.exchangecall;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.open.trade.configuration.WebClientProvider;
import com.open.trade.data.Candle;
import com.open.trade.exchangecall.exchange.KrakenResponse;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
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
                                                        23)
                                        )
                                )
                        )
                ));

        mockWebServer.enqueue(mockResponse);

        Candle[] candles = krakenCall.engulfingCandles("BTCUSD", 60, 2);

        assertThat(candles).isEqualTo(
                new Candle[]{
                        new Candle(
                                30306.1f,
                                30306.2f,
                                30305.7f,
                                30305.7f
                        ),
                        new Candle(
                                30306.1f,
                                30306.2f,
                                30305.7f,
                                30305.7f
                        )
                }
        );
    }

}
