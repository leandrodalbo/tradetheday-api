package com.open.trade.data.kraken;

public record KrakenOrderPostBody(
        long nonce,
        String ordertype,
        String type,
        double volume,
        String pair
) {
    public String asParams() {
        StringBuilder asParams = new StringBuilder();

        asParams.append("nonce=");
        asParams.append(nonce);
        asParams.append("&ordertype=");
        asParams.append(ordertype);
        asParams.append("&type=");
        asParams.append(type);
        asParams.append("&volume=");
        asParams.append(volume);
        asParams.append("&pair=");
        asParams.append(pair);

        return asParams.toString();
    }
}
