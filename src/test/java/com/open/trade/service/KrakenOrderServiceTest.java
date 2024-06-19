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

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
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

        when(krakenCall.postOrder(any())).thenReturn(Mono.just(new KrakenPostResult(true, "success")));

        Mono<KrakenPostResult> result = service.postOrder("SOLUSD", 1.2, 43.4, KrakenBuySell.BUY);

        StepVerifier.create(result)
                .thenConsumeWhile(KrakenPostResult::success);

        verify(krakenCall, times(1)).postOrder(any());
    }


    @Test
    void willOpenAnewTrade() {
        when(props.apiKey()).thenReturn("aber23v");
        when(props.apiSecret()).thenReturn("aber23v");


        when(krakenCall.postOrder(any())).thenReturn(Mono.just(new KrakenPostResult(true, "success")));

        Mono<Trade> result = service.newTrade(new OpenTrade(
                "SOLUSD",
                2.3,
                43.4,
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


        when(krakenCall.postOrder(any())).thenReturn(Mono.just(new KrakenPostResult(false, "no-success")));

        Mono<Trade> result = service.newTrade(new OpenTrade(
                "SdLUSD",
                2.3,
                43.4,
                54.0,
                50.1
        ));

        StepVerifier.create(result)
                .expectError();

        verify(krakenCall, times(1)).postOrder(any());
    }


    @Test
    void WillGenerateSignature() throws NoSuchAlgorithmException, InvalidKeyException {
        String pk = "kQH5HW/8p1uGOVjbgWA7FunAmGO8lsSUXNsu3eow76sz84Q18fWxnyRzBHCd3pd5nE9qa99HAZtuZuj6F1huXg==";
        String nonce = "1616492376594";
        String data = "nonce=1616492376594&ordertype=limit&pair=XBTUSD&price=37500&type=buy&volume=1.25";
        String path = "/0/private/AddOrder";

        assertThat(service.signature(pk, data, nonce, path)).isEqualTo("4/dpxb3iT4tp/ZCVEwSnEsLxx0bqyhLpdfOpc6fn7OR8+UClSV5n9E6aSS8MPtnRfp32bAb0nmbRn6H8ndwLUQ==");
    }
}
