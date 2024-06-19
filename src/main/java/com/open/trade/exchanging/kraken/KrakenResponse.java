package com.open.trade.exchanging.kraken;

public record KrakenResponse(
        String[] error,
        Object result) {
}
