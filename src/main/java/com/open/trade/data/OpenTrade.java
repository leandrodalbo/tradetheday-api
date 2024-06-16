package com.open.trade.data;

public record OpenTrade(String symbol,
                        double volume,
                        double profitprice,
                        double stopprice) {
}
