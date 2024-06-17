package com.open.trade.service;


import com.open.trade.configuration.KrakenProps;
import com.open.trade.data.Candle;
import com.open.trade.exchangecall.KrakenCall;
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
public class EngulfingKrakenTradesTest {

    @Mock
    OpportunityRepository repository;

    @Mock
    KrakenCall krakenCall;

    @Mock
    EngulfingCandleStrategy strategy;

    @Mock
    KrakenProps props;


    @InjectMocks
    EngulfingKrakenTrades search;

    @Test
    void shouldUpdateOpportunities() {
        when(props.symbols()).thenReturn(Set.of("BTCUSDT"));
        when(repository.findBySymbol(any())).thenReturn(Mono.just(
                Opportunity.of(
                        "BTCUSDT",
                        Speed.HIGH,
                        true,
                        3000.00F,
                        3000.00F,
                        3000.00F,
                        Speed.HIGH,
                        false,
                        0.0f,
                        0.0f,
                        0.0f
                )
        ));
        when(repository.save(any())).thenReturn(Mono.empty());
        when(krakenCall.engulfingCandles(any(), any())).thenReturn(
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
        verify(repository, times(1)).findBySymbol(any());
        verify(krakenCall, times(1)).engulfingCandles(any(), any());
    }

    @Test
    void shouldSaveNewOpportunities() {
        when(props.symbols()).thenReturn(Set.of("BTCUSDT"));
        when(repository.findBySymbol(any())).thenReturn(Mono.error(new Exception("FAILED")));
        when(repository.save(any())).thenReturn(Mono.empty());
        when(krakenCall.engulfingCandles(any(), any())).thenReturn(
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
        verify(repository, times(1)).findBySymbol(any());
        verify(krakenCall, times(1)).engulfingCandles(any(), any());
    }
}
