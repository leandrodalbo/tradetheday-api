package com.tradetheday.exchanging.kraken;

public record KrakenStopLoss(String symbol,
                             double volume,
                             double trigger
) {
}
