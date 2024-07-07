package com.tradetheday.service;


import com.tradetheday.configuration.BinanceProps;
import com.tradetheday.exchangecall.BinanceCall;
import com.tradetheday.exchanging.Candle;
import com.tradetheday.model.Opportunity;
import com.tradetheday.model.Timeframe;
import com.tradetheday.repository.OpportunityRepository;
import com.tradetheday.strategy.EngulfingCandleStrategy;
import com.tradetheday.strategy.MACandleStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class BinanceSearchServiceTest {

    @Mock
    OpportunityRepository repository;

    @Mock
    BinanceCall binanceCall;

    @Mock
    EngulfingCandleStrategy engulfingStrategy;

    @Mock
    MACandleStrategy maStrategy;

    @Mock
    BinanceProps props;

    @InjectMocks
    BinanceSearchService search;

    @Test
    void shouldSearchAndUpdateEngulfingOpportunities() {
        when(props.profit()).thenReturn(1.4f);
        when(props.stop()).thenReturn(1.0f);
        when(repository.findById(anyString())).thenReturn(Mono.just(
                new Opportunity(
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
                )
        ));
        when(repository.save(any())).thenReturn(Mono.empty());
        when(binanceCall.engulfingCandles(any(), any())).thenReturn(
                Mono.just(new Candle[]{
                        Candle.of(1.5f, 1.8f, 0.5f, 1.0f),
                        Candle.of(0.9f, 5.0f, 4.5f, 2.5f),
                        Candle.of(0.9f, 5.0f, 4.5f, 2.5f)
                })
        );
        when(engulfingStrategy.isOn(any())).thenReturn(true);

        search.searchEngulfingCandles("BTCUSDT", Timeframe.H1, props);

        verify(props, times(1)).profit();
        verify(props, times(1)).stop();
        verify(repository, times(1)).save(any());
        verify(repository, times(1)).findById(anyString());
        verify(binanceCall, times(1)).engulfingCandles(any(), any());
    }

    @Test
    void shouldSearchNewEngulfingOpportunities() {
        when(props.profit()).thenReturn(1.4f);
        when(props.stop()).thenReturn(1.0f);

        when(repository.findById(anyString())).thenReturn(Mono.empty());
        when(repository.save(any())).thenReturn(Mono.empty());
        when(binanceCall.engulfingCandles(any(), any())).thenReturn(
                Mono.just(new Candle[]{
                        Candle.of(1.5f, 1.8f, 0.5f, 1.0f),
                        Candle.of(0.9f, 5.0f, 4.5f, 2.5f),
                        Candle.of(0.9f, 5.0f, 4.5f, 2.5f)
                })
        );
        when(engulfingStrategy.isOn(any())).thenReturn(true);

        search.searchEngulfingCandles("BTCUSDT", Timeframe.H1, props);

        verify(props, times(1)).profit();
        verify(props, times(1)).stop();

        verify(repository, times(1)).save(any());
        verify(repository, times(1)).findById(anyString());
        verify(binanceCall, times(1)).engulfingCandles(any(), any());
    }

    @Test
    void shouldSearchNewMaOpportunities() {
        when(props.profit()).thenReturn(1.4f);
        when(props.stop()).thenReturn(1.0f);
        when(props.extracandles()).thenReturn(10);
        when(props.longma()).thenReturn(21);
        when(props.shortma()).thenReturn(9);

        when(repository.findById(anyString())).thenReturn(Mono.empty());
        when(repository.save(any())).thenReturn(Mono.empty());
        when(binanceCall.MACandles(any(), any(), anyInt())).thenReturn(
                Mono.just(new Candle[]{
                        Candle.of(1.5f, 1.8f, 0.5f, 1.0f),
                        Candle.of(0.9f, 5.0f, 4.5f, 2.5f),
                        Candle.of(0.9f, 5.0f, 4.5f, 2.5f)
                })
        );
        when(maStrategy.isOn(any())).thenReturn(true);

        search.searchMACrossOver("BTCUSDT", Timeframe.H1, props);

        verify(props, times(1)).profit();
        verify(props, times(1)).stop();
        verify(props, times(1)).shortma();
        verify(props, times(2)).longma();
        verify(props, times(2)).extracandles();
        verify(repository, times(1)).save(any());
        verify(repository, times(1)).findById(anyString());
        verify(binanceCall, times(1)).MACandles(any(), any(), anyInt());
    }


    @Test
    void shouldSearchAndUpdateMAOpportunities() {
        when(props.profit()).thenReturn(1.4f);
        when(props.stop()).thenReturn(1.0f);
        when(props.extracandles()).thenReturn(10);
        when(props.longma()).thenReturn(21);
        when(props.shortma()).thenReturn(9);

        when(repository.findById(anyString())).thenReturn(Mono.just(
                new Opportunity(
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
                )
        ));
        when(repository.save(any())).thenReturn(Mono.empty());
        when(binanceCall.MACandles(any(), any(), anyInt())).thenReturn(
                Mono.just(new Candle[]{
                        Candle.of(1.5f, 1.8f, 0.5f, 1.0f),
                        Candle.of(0.9f, 5.0f, 4.5f, 2.5f),
                        Candle.of(0.9f, 5.0f, 4.5f, 2.5f)
                })
        );
        when(maStrategy.isOn(any())).thenReturn(true);

        search.searchMACrossOver("BTCUSDT", Timeframe.H1, props);

        verify(props, times(1)).profit();
        verify(props, times(1)).stop();
        verify(props, times(1)).shortma();
        verify(props, times(2)).longma();
        verify(props, times(2)).extracandles();
        verify(repository, times(1)).save(any());
        verify(repository, times(1)).findById(anyString());
        verify(binanceCall, times(1)).MACandles(any(), any(), anyInt());
    }
}
