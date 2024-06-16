package com.open.trade.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

import java.time.Instant;

public record Trade(
        @Id
        Long id,
        String symbol,
        double volume,
        double profitprice,
        double stopprice,
        TradeStatus status,
        Long ondatetime,
        @Version
        Integer version
) {
    public static Trade of(String symbol,
                           double volume,
                           double profitprice,
                           double stopprice,
                           TradeStatus status
    ) {
        return new Trade(null,
                symbol,
                volume,
                profitprice,
                stopprice,
                status,
                Instant.now().getEpochSecond(),
                null);
    }
}
