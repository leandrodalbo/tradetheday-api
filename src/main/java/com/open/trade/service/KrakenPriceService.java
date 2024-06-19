package com.open.trade.service;

import com.open.trade.exchangecall.KrakenCall;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class KrakenPriceService {

    private final KrakenCall krakenCall;

    public KrakenPriceService(KrakenCall krakenCall) {
        this.krakenCall = krakenCall;
    }

    public Mono<Double> latestPrice(String symbol) {
        return krakenCall.latestPrice(symbol).map(it -> it);
    }

}
