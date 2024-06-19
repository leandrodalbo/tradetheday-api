package com.open.trade.exchanging.kraken;

public record KrakenOrderPost(
        String apiKey,
        String signature,
        String data
) {
}
