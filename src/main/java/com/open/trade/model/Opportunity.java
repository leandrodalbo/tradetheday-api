package com.open.trade.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

public record Opportunity(

        @Id
        Long id,

        String symbol,

        String binancetimeframe,

        Boolean binanceengulfing,

        Float binanceprice,

        Float binancestop,

        Float binanceprofit,

        Long krakentimeframe,

        Boolean krakenengulfing,

        Float krakenprice,

        Float krakenstop,

        Float krakenprofit,

        @Version
        Integer version
) {

    public static Opportunity of(
            String symbol,
            String binanceTimeframe,
            Boolean binanceEngulfing,
            Float binancePrice,
            Float binanceStop,
            Float binanceProfit,
            Long krakenTimeFrame,
            Boolean krakenEngulfing,
            Float krakenPrice,
            Float krakenStop,
            Float krakenProfit
    ) {
        return new Opportunity(
                null,
                symbol,
                binanceTimeframe,
                binanceEngulfing,
                binancePrice,
                binanceStop,
                binanceProfit,
                krakenTimeFrame,
                krakenEngulfing,
                krakenPrice,
                krakenStop,
                krakenProfit,
                null
        );
    }
}
