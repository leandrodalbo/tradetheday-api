package com.open.trade.data;

public record OpenTrade(String symbol,
                        double volume,
                        double price,
                        double profitprice,
                        double stopprice) {
}
