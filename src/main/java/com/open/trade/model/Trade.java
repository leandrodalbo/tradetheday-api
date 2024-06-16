package com.open.trade.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

public record Trade(
        @Id
        Long id,
        String symbol,
        double volume,
        double profitprice,
        double stopprice,
        @Version
        Integer version
) {
    public static Trade of(String symbol,
                           double volume,
                           double profitprice,
                           double stopprice) {
        return new Trade(null,
                symbol,
                volume,
                profitprice,
                stopprice,
                null);
    }
}
