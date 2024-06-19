package com.open.trade.exchanging;

public record OpenTrade(String symbol,
                        double volume,
                        double price,
                        double profitprice,
                        double stopprice) {
}
