package com.tradetheday.service;

import com.tradetheday.configuration.KrakenProps;
import com.tradetheday.exchangecall.KrakenCall;
import com.tradetheday.exchanging.kraken.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class KrakenOrderServiceTest {

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

        Mono<KrakenPostResult> result = service.postOrder("SOLUSD", 1.2, KrakenBuySell.BUY, KrakenOrderType.MARKET, Optional.empty());

        StepVerifier.create(result)
                .thenConsumeWhile(KrakenPostResult::success);

        verify(krakenCall, times(1)).postOrder(any());
    }

    @Test
    void willPostAStopLossOrder() {
        when(props.apiKey()).thenReturn("aber23v");
        when(props.apiSecret()).thenReturn("aber23v");

        when(krakenCall.postOrder(any())).thenReturn(Mono.just(new KrakenPostResult(true, "success")));

        Mono<KrakenPostResult> result = service.postOrder("SOLUSD", 1.2, KrakenBuySell.BUY, KrakenOrderType.STOP_LOSS, Optional.of(3.4));

        StepVerifier.create(result)
                .thenConsumeWhile(KrakenPostResult::success);

        verify(krakenCall, times(1)).postOrder(any());
    }


    @Test
    void ShouldDareToEnterTheMarket() {
        when(props.apiKey()).thenReturn("aber23v");
        when(props.apiSecret()).thenReturn("aber23v");
        when(props.symbols()).thenReturn(Set.of("SOLUSD"));


        when(krakenCall.postOrder(any())).thenReturn(Mono.just(new KrakenPostResult(true, "success")));

        Mono<String> result = service.dareToEnter(new KrakenMarketBuy(
                "SOLUSD",
                2.3));

        StepVerifier.create(result)
                .thenConsumeWhile("Order Completed"::equals);

        verify(krakenCall, times(1)).postOrder(any());
    }

    @Test
    void WillValidateKrakenSymbolPair() {
        when(props.symbols()).thenReturn(Set.of("XRPUSDT"));

        Mono<String> result = service.dareToEnter(new KrakenMarketBuy(
                "XRPUSD",
                100
        ));

        StepVerifier.create(result)
                .thenConsumeWhile("Invalid Kraken symbol"::equals);

        verify(krakenCall, times(0)).postOrder(any());
    }


    @Test
    void willGetAnErrorWhenItFailed() {
        when(props.apiKey()).thenReturn("aber23v");
        when(props.apiSecret()).thenReturn("aber23v");
        when(props.symbols()).thenReturn(Set.of("SOLUSD"));

        when(krakenCall.postOrder(any())).thenReturn(Mono.just(new KrakenPostResult(false, "no-success")));

        Mono<String> result = service.dareToEnter(new KrakenMarketBuy(
                "SOLUSD",
                2.3
        ));

        StepVerifier.create(result)
                .expectError();

        verify(krakenCall, times(1)).postOrder(any());
    }


    @Test
    void ShouldSetStopLoss() {
        when(props.apiKey()).thenReturn("aber23v");
        when(props.apiSecret()).thenReturn("aber23v");
        when(props.symbols()).thenReturn(Set.of("SOLUSD"));


        when(krakenCall.postOrder(any())).thenReturn(Mono.just(new KrakenPostResult(true, "success")));

        Mono<String> result = service.setStopLoss(new KrakenConditionalOrderData(
                "SOLUSD",
                23.2,
                55.0
        ));

        StepVerifier.create(result)
                .thenConsumeWhile("Stop-Loss order completed"::equals);

        verify(krakenCall, times(1)).postOrder(any());
    }

    @Test
    void ShouldSetTakeProfit() {
        when(props.apiKey()).thenReturn("aber23v");
        when(props.apiSecret()).thenReturn("aber23v");
        when(props.symbols()).thenReturn(Set.of("SOLUSD"));


        when(krakenCall.postOrder(any())).thenReturn(Mono.just(new KrakenPostResult(true, "success")));

        Mono<String> result = service.setTakeProfit(new KrakenConditionalOrderData(
                "SOLUSD",
                23.2,
                55.0
        ));

        StepVerifier.create(result)
                .thenConsumeWhile("Take-Profit order completed"::equals);

        verify(krakenCall, times(1)).postOrder(any());
    }

    @Test
    void WillValidateStopKrakenSymbolPair() {
        when(props.symbols()).thenReturn(Set.of("XRPUSDT"));

        Mono<String> result = service.setStopLoss(new KrakenConditionalOrderData(
                "SOLUSDT",
                23.2,
                55.0
        ));

        StepVerifier.create(result)
                .thenConsumeWhile("Invalid Kraken symbol"::equals);

        verify(krakenCall, times(0)).postOrder(any());
    }


    @Test
    void willGetAnErrorWhenStopLossOrderFailed() {
        when(props.apiKey()).thenReturn("aber23v");
        when(props.apiSecret()).thenReturn("aber23v");
        when(props.symbols()).thenReturn(Set.of("SOLUSD"));

        when(krakenCall.postOrder(any())).thenReturn(Mono.just(new KrakenPostResult(false, "no-success")));

        Mono<String> result = service.setStopLoss(new KrakenConditionalOrderData(
                "SOLUSD",
                23.2,
                55.0
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

    @Test
    void shouldEncodeData() {
        Map<String, String> params = new HashMap<>();

        params.put("nonce", "13242424243744");
        params.put("ordertype", "market");
        params.put("pair", "BTCUSDT");
        params.put("type", KrakenBuySell.BUY.name().toLowerCase());
        params.put("volume", "0.01");

        String encoded = "volume=0.01&type=buy&nonce=13242424243744&pair=BTCUSDT&ordertype=market";

        assertThat(service.postingData(params)).isEqualTo(encoded);
    }
}
