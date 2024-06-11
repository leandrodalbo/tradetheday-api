package com.open.trade.call.exchange;

public record KrakenResponse(
        String[] error,
        Object result) {
}
