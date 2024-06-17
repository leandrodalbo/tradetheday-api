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
        TradeStatus tradestatus,
        Long ondatetime,
        TradeResult traderesult,
        Boolean isakrakentrade,
        @Version
        Integer version
) {
    public static Trade of(String symbol,
                           double volume,
                           double profitprice,
                           double stopprice,
                           TradeStatus tradestatus,
                           Boolean isakrakentrade
    ) {
        return new Trade(null,
                symbol,
                volume,
                profitprice,
                stopprice,
                tradestatus,
                Instant.now().getEpochSecond(),
                null,
                isakrakentrade,
                null
        );
    }
}
