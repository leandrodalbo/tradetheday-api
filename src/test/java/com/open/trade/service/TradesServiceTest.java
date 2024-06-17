package com.open.trade.service;

import com.open.trade.model.Trade;
import com.open.trade.model.TradeResult;
import com.open.trade.model.TradeStatus;
import com.open.trade.repository.TradeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TradesServiceTest {

    @Mock
    TradeRepository repository;


    @InjectMocks
    TradesService service;


    @Test
    void filterByResultStatusAndDate() {
        long date = Instant.now().getEpochSecond();
        Trade t = new Trade(1L, "BTCUSD", 0.1, 55000, 50000, TradeStatus.CLOSED, date, TradeResult.SUCCESS, false, 0);

        assertThat(service.tradeFilter(t, Optional.of(TradeResult.SUCCESS), Optional.of(TradeStatus.CLOSED), Optional.of(true))).isTrue();
    }

    @Test
    void filterByResultAndStatus() {
        Trade t = new Trade(1L, "BTCUSD", 0.1, 55000, 50000, TradeStatus.CLOSED, Instant.now().minus(2, ChronoUnit.DAYS).getEpochSecond(), TradeResult.SUCCESS, false, 0);

        assertThat(service.tradeFilter(t, Optional.of(TradeResult.SUCCESS), Optional.of(TradeStatus.CLOSED), Optional.empty())).isTrue();
    }

    @Test
    void filterByStatusAndDate() {
        long date = Instant.now().getEpochSecond();
        Trade t = new Trade(1L, "BTCUSD", 0.1, 55000, 50000, TradeStatus.CLOSED, date, TradeResult.FAILED, false, 0);

        assertThat(service.tradeFilter(t, Optional.empty(), Optional.of(TradeStatus.CLOSED), Optional.of(true))).isTrue();
    }

    @Test
    void filterByResultAndDate() {
        long date = Instant.now().getEpochSecond();
        Trade t = new Trade(1L, "BTCUSD", 0.1, 55000, 50000, TradeStatus.CLOSED, date, TradeResult.SUCCESS, false, 0);

        assertThat(service.tradeFilter(t, Optional.of(TradeResult.SUCCESS), Optional.empty(), Optional.of(true))).isTrue();
    }

    @Test
    void filterByDate() {
        long date = Instant.now().getEpochSecond();
        Trade t = new Trade(1L, "BTCUSD", 0.1, 55000, 50000, TradeStatus.CLOSED, date, TradeResult.SUCCESS, false, 0);

        assertThat(service.tradeFilter(t, Optional.empty(), Optional.empty(), Optional.of(true))).isTrue();
    }

    @Test
    void filterByResult() {
        long date = Instant.now().getEpochSecond();
        Trade t = new Trade(1L, "BTCUSD", 0.1, 55000, 50000, TradeStatus.CLOSED, date, TradeResult.SUCCESS, false, 0);

        assertThat(service.tradeFilter(t, Optional.of(TradeResult.SUCCESS), Optional.empty(), Optional.empty())).isTrue();
    }

    @Test
    void filterByStatus() {
        long date = Instant.now().getEpochSecond();
        Trade t = new Trade(1L, "BTCUSD", 0.1, 55000, 50000, TradeStatus.CLOSED, date, TradeResult.SUCCESS, false, 0);

        assertThat(service.tradeFilter(t, Optional.empty(), Optional.of(TradeStatus.CLOSED), Optional.empty())).isTrue();
    }

    @Test
    void isTrueWithoutFilters() {
        long date = Instant.now().getEpochSecond();
        Trade t = new Trade(1L, "BTCUSD", 0.1, 55000, 50000, TradeStatus.CLOSED, date, TradeResult.SUCCESS, false, 0);

        assertThat(service.tradeFilter(t, Optional.empty(), Optional.empty(), Optional.empty())).isTrue();
    }


    @Test
    void findTrades() {
        when(repository.findAll()).thenReturn(
                Flux.just(
                        Trade.of("BTCUSD", 0.01, 54.033, 50.002, TradeStatus.OPEN, true),
                        Trade.of("BTCUSD", 0.01, 54.033, 50.002, TradeStatus.CLOSED, false)
                )
        );

        Flux<Trade> result = service.findTrades(Optional.of(TradeStatus.CLOSED), Optional.empty(), Optional.empty());

        StepVerifier.create(result)
                .thenConsumeWhile(it -> it.tradestatus().equals(TradeStatus.CLOSED))
                .verifyComplete();

        verify(repository, times(1)).findAll();
    }

}
