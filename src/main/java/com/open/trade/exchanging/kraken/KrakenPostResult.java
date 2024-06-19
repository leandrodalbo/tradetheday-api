package com.open.trade.exchanging.kraken;

public record KrakenPostResult(
        Boolean success,
        String message
) {
}
