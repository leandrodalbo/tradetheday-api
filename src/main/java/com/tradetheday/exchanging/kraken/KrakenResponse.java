package com.tradetheday.exchanging.kraken;

public record KrakenResponse(
        String[] error,
        Object result) {
}
