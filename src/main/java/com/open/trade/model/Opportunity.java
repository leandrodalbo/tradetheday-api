package com.open.trade.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

public record Opportunity(

        @Id
        Long id,

        String symbol,

        String binancetimeframe,

        Boolean binanceentry,

        Float binanceprice,

        Float binancestop,

        Float binanceprofit,

        Long krakentimeframe,

        Boolean krakenentry,

        Float krakenprice,

        Float krakenstop,


        Float krakenprofit,

        @Version
        Integer version
) {

    public static Opportunity of(
            String symbol,
            String binanceTimeframe,
            Boolean binanceEntry,
            Float binancePrice,
            Float binanceStop,
            Float binanceProfit,
            Long krakenTimeFrame,
            Boolean krakenEntry,
            Float krakenPrice,
            Float krakenStop,
            Float krakenProfit
    ) {
        return new Opportunity(
                null,
                symbol,
                binanceTimeframe,
                binanceEntry,
                binancePrice,
                binanceStop,
                binanceProfit,
                krakenTimeFrame,
                krakenEntry,
                krakenPrice,
                krakenStop,
                krakenProfit,
                null
        );
    }
}
