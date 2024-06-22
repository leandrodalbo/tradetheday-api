package com.tradetheday.strategy;


import com.tradetheday.exchanging.Candle;
import org.springframework.stereotype.Component;

@Component
public class MACandleStrategy implements Strategy<MACandleStrategy.MAStrategyData> {

    @Override
    public Boolean isOn(MAStrategyData data) {
        double shortSum = 0;
        double longSum = 0;
        int shortPeriod = data.shortPeriod;
        int longPeriod = data.longPeriod;

        for (int i = data.candles.length - 1; i >= 0; i--) {
            if (shortPeriod > 0) {
                shortSum += data.candles[i].close();
            }

            if (longPeriod > 0) {
                longSum += data.candles[i].close();
            }

            shortPeriod--;
            longPeriod--;
        }

        return ((shortSum / data.shortPeriod) > (longSum / data.longPeriod));
    }


    public record MAStrategyData(Candle[] candles, int shortPeriod, int longPeriod) {
    }
}
