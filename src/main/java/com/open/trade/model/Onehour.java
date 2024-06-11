package com.open.trade.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

@Table("onehour")
public record Onehour(

        @Id
        String symbol,

        Boolean binanceEntry,

        Float binancePrice,

        Float binanceStop,

        Float binanceProfit,

        Boolean krakenEntry,

        Float krakenPrice,

        Float krakenStop,


        Float krakenProfit,

        @Version
        Integer version
) {
}
