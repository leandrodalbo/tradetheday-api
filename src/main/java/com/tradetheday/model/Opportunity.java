package com.tradetheday.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Table
public record Opportunity(

        @Id
        String symbolspeed,

        Boolean binanceengulfing,

        Boolean binancema,

        Float binanceprice,

        Float binancestop,

        Float binanceprofit,

        Boolean krakenengulfing,

        Boolean krakenma,

        Float krakenprice,

        Float krakenstop,

        Float krakenprofit,

        Long ondatetime,

        @Version
        Integer version
) {

    public static Opportunity of(
            String symbol,
            Timeframe speed,
            Boolean binanceEngulfing,
            Boolean binancema,
            Float binancePrice,
            Float binanceStop,
            Float binanceProfit,
            Boolean krakenEngulfing,
            Boolean krakenma,
            Float krakenPrice,
            Float krakenStop,
            Float krakenProfit
    ) {
        return new Opportunity(
                generateSimbolSpeed(symbol, speed),
                binanceEngulfing,
                binancema,
                binancePrice,
                binanceStop,
                binanceProfit,
                krakenEngulfing,
                krakenma,
                krakenPrice,
                krakenStop,
                krakenProfit,
                Instant.now().getEpochSecond(),
                null
        );
    }

    public static String generateSimbolSpeed(String symbol, Timeframe tf) {
        return (symbol + "-" + tf.toString());
    }
}
