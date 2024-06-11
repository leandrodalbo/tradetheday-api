package com.open.trade.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("trade_one_day")
public record TradeOneDay(

        @Id
        @Column("symbol")
        String symbol,

        @Column("binance_entry")
        Boolean binanceEntry,

        @Column("binance_price")
        Float binancePrice,

        @Column("binance_stop")
        Float binanceStop,

        @Column("binance_profit")
        Float binanceProfit,

        @Column("kraken_entry")
        Boolean krakenEntry,

        @Column("kraken_price")
        Float krakenPrice,

        @Column("kraken_stop")
        Float krakenStop,


        @Column("kraken_profit")
        Float krakenProfit,

        @Version
        Integer version
) {
}
