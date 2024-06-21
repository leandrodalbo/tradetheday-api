package com.open.trade.exchanging.kraken;

public record KrakenStopLoss(String symbol,
                             double volume,
                             double trigger
) {
}
