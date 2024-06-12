package com.open.trade.service;


import com.open.trade.configuration.BinanceProps;
import com.open.trade.data.Candle;
import com.open.trade.exchangecall.BinanceCall;
import com.open.trade.model.Opportunity;
import com.open.trade.repository.OpportunityRepository;
import com.open.trade.strategy.EngulfingCandleStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class EngulfingKrakenSearchTest {

    @Mock
    OpportunityRepository repository;

    @Mock
    BinanceCall binanceCall;

    @Mock
    EngulfingCandleStrategy strategy;

    @Mock
    BinanceProps props;


    @InjectMocks
    EngulfingBinanceSearch search;


    @Test
    void shouldSearchBinanceOpportunitiesAndSaveThem() {
        when(props.symbols()).thenReturn(Set.of("BTCUSDT"));
        when(repository.save(any())).thenReturn(mock(Opportunity.class));
        when(binanceCall.engulfingCandles(any(), anyString(), anyInt())).thenReturn(
                new Candle[]{
                        Candle.of(1.5f, 1.8f, 0.5f, 1.0f),
                        Candle.of(0.9f, 5.0f, 4.5f, 2.5f)
                }
        );

        when(strategy.isEngulfing(any())).thenReturn(true);

        search.searchEntries("1h");

        verify(props, times(1)).symbols();
        verify(repository, times(1)).save(any());
        verify(binanceCall, times(1)).engulfingCandles(any(), anyString(), anyInt());

    }
}
