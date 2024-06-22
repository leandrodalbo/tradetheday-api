package com.tradetheday.exchanging.kraken;

public record KrakenOrderPost(
        String apiKey,
        String signature,
        String data
) {
}
