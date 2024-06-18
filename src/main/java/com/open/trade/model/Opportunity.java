package com.open.trade.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Table
public record Opportunity(

        @Id
        String symbolspeed,

        Boolean binanceengulfing,

        Float binanceprice,

        Float binancestop,

        Float binanceprofit,

        Boolean krakenengulfing,

        Float krakenprice,

        Float krakenstop,

        Float krakenprofit,

        Long ondatetime,

        @Version
        Integer version
) {

    public static Opportunity of(
            String symbol,
            Speed speed,
            Boolean binanceEngulfing,
            Float binancePrice,
            Float binanceStop,
            Float binanceProfit,
            Boolean krakenEngulfing,
            Float krakenPrice,
            Float krakenStop,
            Float krakenProfit
    ) {
        return new Opportunity(
                generateSimbolSpeed(symbol, speed),
                binanceEngulfing,
                binancePrice,
                binanceStop,
                binanceProfit,
                krakenEngulfing,
                krakenPrice,
                krakenStop,
                krakenProfit,
                Instant.now().getEpochSecond(),
                null
        );
    }

    public static String generateSimbolSpeed(String symbol, Speed speed) {
        return (symbol + "-" + speed.toString());
    }
}
