package com.tradetheday.exchanging.kraken;

public record KrakenConditionalOrderData(String symbol,
                                         double volume,
                                         double trigger
) {
}
