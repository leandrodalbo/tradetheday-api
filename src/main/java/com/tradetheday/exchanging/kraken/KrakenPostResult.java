package com.tradetheday.exchanging.kraken;

public record KrakenPostResult(
        Boolean success,
        String message
) {
}
