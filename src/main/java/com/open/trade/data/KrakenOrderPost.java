package com.open.trade.data;

public record KrakenOrderPost(
        String apiKey,
        String signature,
        String uriPath,
        KrakenOrderPostBody body
) {
}
