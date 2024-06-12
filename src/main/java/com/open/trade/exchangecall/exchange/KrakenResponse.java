package com.open.trade.exchangecall.exchange;

public record KrakenResponse(
        String[] error,
        Object result) {
}
