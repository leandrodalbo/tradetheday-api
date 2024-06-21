package com.open.trade.service;


import com.open.trade.configuration.BinanceProps;
import com.open.trade.exchangecall.BinanceCall;
import com.open.trade.exchanging.Candle;
import com.open.trade.model.Opportunity;
import com.open.trade.model.Speed;
import com.open.trade.repository.OpportunityRepository;
import com.open.trade.strategy.EngulfingCandleStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class EngulfingBinanceTradesTest {

    @Mock
    OpportunityRepository repository;

    @Mock
    BinanceCall binanceCall;

    @Mock
    EngulfingCandleStrategy strategy;

    @Mock
    BinanceProps props;

    @InjectMocks
    EngulfingBinanceTrades search;

    @Test
    void shouldUpdateOpportunities() {
        when(props.symbols()).thenReturn(Set.of("BTCUSDT"));
        when(repository.findById(anyString())).thenReturn(Mono.just(
                Opportunity.of(
                        "BTCUSDT",
                        Speed.HIGH,
                        true,
                        3000.00F,
                        3000.00F,
                        3000.00F,
                        false,
                        0.0f,
                        0.0f,
                        0.0f
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
        when(strategy.isEngulfing(any())).thenReturn(true);

        search.searchEntries(Speed.HIGH);

        verify(props, times(1)).symbols();
        verify(repository, times(1)).save(any());
        verify(repository, times(1)).findById(anyString());
        verify(binanceCall, times(1)).engulfingCandles(any(), any());
    }

    @Test
    void shouldSaveNewOpportunities() {
        when(props.symbols()).thenReturn(Set.of("BTCUSDT"));
        when(repository.findById(anyString())).thenReturn(Mono.empty());
        when(repository.save(any())).thenReturn(Mono.empty());
        when(binanceCall.engulfingCandles(any(), any())).thenReturn(
                Mono.just(new Candle[]{
                        Candle.of(1.5f, 1.8f, 0.5f, 1.0f),
                        Candle.of(0.9f, 5.0f, 4.5f, 2.5f),
                        Candle.of(0.9f, 5.0f, 4.5f, 2.5f)
                })
        );
        when(strategy.isEngulfing(any())).thenReturn(true);

        search.searchEntries(Speed.HIGH);

        verify(props, times(1)).symbols();
        verify(repository, times(1)).save(any());
        verify(repository, times(1)).findById(anyString());
        verify(binanceCall, times(1)).engulfingCandles(any(), any());
    }
}
