package com.open.trade.service;

import com.open.trade.configuration.KrakenProps;
import com.open.trade.data.OpenTrade;
import com.open.trade.data.kraken.KrakenBuySell;
import com.open.trade.data.kraken.KrakenPostResult;
import com.open.trade.exchangecall.KrakenCall;
import com.open.trade.model.Trade;
import com.open.trade.model.TradeStatus;
import com.open.trade.repository.TradeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class KrakenOrderServiceTest {

    @Mock
    TradeRepository repository;

    @Mock
    KrakenCall krakenCall;

    @Mock
    KrakenProps props;

    @InjectMocks
    KrakenOrderService service;

    @Test
    void willPostAMarketOrder() {
        when(props.apiKey()).thenReturn("aber23v");
        when(props.apiSecret()).thenReturn("aber23v");
        when(props.macAlgorithm()).thenReturn("HmacSHA512");
        when(props.shaAlgorithm()).thenReturn("SHA-256");
        when(props.privateUriPath()).thenReturn("/0/private/AddOrder");
        when(props.orderType()).thenReturn("market");

        when(krakenCall.postOrder(any())).thenReturn(Mono.just(new KrakenPostResult(true, "success")));

        Mono<KrakenPostResult> result = service.postOrder("SOLUSD", 1.2, KrakenBuySell.BUY);

        StepVerifier.create(result)
                .thenConsumeWhile(KrakenPostResult::success);

        verify(krakenCall, times(1)).postOrder(any());
    }


    @Test
    void willOpenAnewTrade() {
        when(props.apiKey()).thenReturn("aber23v");
        when(props.apiSecret()).thenReturn("aber23v");
        when(props.macAlgorithm()).thenReturn("HmacSHA512");
        when(props.shaAlgorithm()).thenReturn("SHA-256");
        when(props.privateUriPath()).thenReturn("/0/private/AddOrder");
        when(props.orderType()).thenReturn("market");

        when(krakenCall.postOrder(any())).thenReturn(Mono.just(new KrakenPostResult(true, "success")));

        Mono<Trade> result = service.newTrade(new OpenTrade(
                "SOLUSD",
                2.3,
                54.0,
                50.1
        ));

        StepVerifier.create(result)
                .thenConsumeWhile(it -> it.tradestatus().equals(TradeStatus.OPEN));

        verify(krakenCall, times(1)).postOrder(any());
    }

    @Test
    void willGetAnErrorWhenItFailed() {
        when(props.apiKey()).thenReturn("aber23v");
        when(props.apiSecret()).thenReturn("aber23v");
        when(props.macAlgorithm()).thenReturn("HmacSHA512");
        when(props.shaAlgorithm()).thenReturn("SHA-256");
        when(props.privateUriPath()).thenReturn("/0/private/AddOrder");
        when(props.orderType()).thenReturn("market");

        when(krakenCall.postOrder(any())).thenReturn(Mono.just(new KrakenPostResult(false, "no-success")));

        Mono<Trade> result = service.newTrade(new OpenTrade(
                "SdLUSD",
                2.3,
                54.0,
                50.1
        ));

        StepVerifier.create(result)
                .expectError();

        verify(krakenCall, times(1)).postOrder(any());
    }
}
