package com.open.trade.data.kraken;

public record KrakenOrderPost(
        String apiKey,
        String signature,
        String uriPath,
        KrakenOrderPostBody body
) {
}
