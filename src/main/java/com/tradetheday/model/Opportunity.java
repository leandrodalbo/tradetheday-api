package com.tradetheday.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.Optional;

@Table
public record Opportunity(

        @Id
        String opportunityid,

        Boolean binanceengulfing,

        Long binanceengulfingtime,

        Boolean binancema,

        Long binancematime,

        Float binanceprice,

        Float binancestop,

        Float binanceprofit,

        Boolean krakenengulfing,

        Long krakenengulfingtime,

        Boolean krakenma,

        Long krakenmatime,

        Float krakenprice,

        Float krakenstop,

        Float krakenprofit,

        @Version
        Integer version
) {

    public static Opportunity of(
            String symbol,
            Timeframe timeframe,
            boolean updateBinance,
            boolean updateKraken,
            Optional<Opportunity> old,
            Optional<Boolean> isEngulfing,
            Optional<Long> engulfingTime,
            Optional<Boolean> isMACrossover,
            Optional<Long> MACrossoverTime,
            Optional<Float> price,
            Optional<Float> stop,
            Optional<Float> profit
    ) {
        return new Opportunity(
                old.isPresent() ? old.get().opportunityid : generateId(symbol, timeframe),

                (updateBinance && isEngulfing.isPresent()) ? isEngulfing.get() : old.isPresent() ? old.get().binanceengulfing() : false,
                (updateBinance && engulfingTime.isPresent()) ? engulfingTime.get() : old.isPresent() ? old.get().binanceengulfingtime() : Instant.now().getEpochSecond(),
                (updateBinance && isMACrossover.isPresent()) ? isMACrossover.get() : old.isPresent() ? old.get().binancema() : false,
                (updateBinance && MACrossoverTime.isPresent()) ? MACrossoverTime.get() : old.isPresent() ? old.get().binancematime() : Instant.now().getEpochSecond(),
                (updateBinance && price.isPresent()) ? price.get() : old.isPresent() ? old.get().binanceprice() : 0f,
                (updateBinance && stop.isPresent()) ? stop.get() : old.isPresent() ? old.get().binancestop() : 0f,
                (updateBinance && profit.isPresent()) ? profit.get() : old.isPresent() ? old.get().binanceprofit() : 0f,

                (updateKraken && isEngulfing.isPresent()) ? isEngulfing.get() : old.isPresent() ? old.get().krakenengulfing() : false,
                (updateKraken && engulfingTime.isPresent()) ? engulfingTime.get() : old.isPresent() ? old.get().krakenengulfingtime() : Instant.now().getEpochSecond(),
                (updateKraken && isMACrossover.isPresent()) ? isMACrossover.get() : old.isPresent() ? old.get().krakenma() : false,
                (updateKraken && MACrossoverTime.isPresent()) ? MACrossoverTime.get() : old.isPresent() ? old.get().krakenmatime() : Instant.now().getEpochSecond(),
                (updateKraken && price.isPresent()) ? price.get() : old.isPresent() ? old.get().krakenprice() : 0f,
                (updateKraken && stop.isPresent()) ? stop.get() : old.isPresent() ? old.get().krakenstop() : 0f,
                (updateKraken && profit.isPresent()) ? profit.get() : old.isPresent() ? old.get().krakenprofit() : 0f,

                old.map(Opportunity::version).orElse(null)
        );
    }


    public static String generateId(String symbol, Timeframe tf) {
        return (symbol + "-" + tf.toString());
    }
}
