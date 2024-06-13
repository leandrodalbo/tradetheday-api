package com.open.trade.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

@Table
public record Opportunity(

        @Id
        Long id,

        String symbol,

        Speed binancespeed,

        Boolean binanceengulfing,

        Float binanceprice,

        Float binancestop,

        Float binanceprofit,

        Speed krakenspeed,

        Boolean krakenengulfing,

        Float krakenprice,

        Float krakenstop,

        Float krakenprofit,

        @Version
        Integer version
) {

    public static Opportunity of(
            String symbol,
            Speed binanceSpeed,
            Boolean binanceEngulfing,
            Float binancePrice,
            Float binanceStop,
            Float binanceProfit,
            Speed krakenSpeed,
            Boolean krakenEngulfing,
            Float krakenPrice,
            Float krakenStop,
            Float krakenProfit
    ) {
        return new Opportunity(
                null,
                symbol,
                binanceSpeed,
                binanceEngulfing,
                binancePrice,
                binanceStop,
                binanceProfit,
                krakenSpeed,
                krakenEngulfing,
                krakenPrice,
                krakenStop,
                krakenProfit,
                null
        );
    }
}
