package com.open.trade.exchangecall;

import com.open.trade.configuration.WebClientProvider;
import com.open.trade.data.Candle;
import com.open.trade.exchangecall.exchange.KrakenResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class KrakenCall extends ExchangeCall {
    private static final String ON_PATH = "/0/public/OHLC";

    public KrakenCall(WebClientProvider clientProvider) {
        super(clientProvider.krakenWebClient());
    }


    public Candle[] engulfingCandles(String symbol, Integer interval, long since) {
        KrakenResponse response = client.get()
                .uri(
                        builder -> builder.path(ON_PATH)
                                .queryParam("pair", symbol)
                                .queryParam("interval", interval)
                                .queryParam("since", since)
                                .build()
                )
                .retrieve()
                .bodyToMono(KrakenResponse.class)
                .block();

        Map data = (Map) response.result();
        return engulfingToArray((List) data.get(symbol));
    }
}
