package com.tradetheday.strategy;


import com.tradetheday.exchanging.Candle;
import org.springframework.stereotype.Component;

@Component
public class MACandleStrategy implements Strategy<MACandleStrategy.MAStrategyData> {

    @Override
    public Boolean isOn(MAStrategyData data) {
        double[] longerMAs = new double[data.extraCandles()];
        double[] shorterMAs = new double[data.extraCandles()];

        float longMASum = 0;
        float shortMASum = 0;

        for (int i = 0; i < data.candles.length; i++) {


            if (i >= data.longPeriod()) {
                longerMAs[i - data.longPeriod()] = longMASum / data.longPeriod();
                shorterMAs[i - data.longPeriod()] = shortMASum / data.shortPeriod();
                longMASum -= data.candles[i - data.longPeriod()].close();
            }

            if (i >= data.shortPeriod()) {
                shortMASum -= data.candles[i - data.shortPeriod()].close();
            }

            longMASum += data.candles[i].close();
            shortMASum += data.candles[i].close();

        }


        for (int i = 1; i < data.extraCandles(); i++) {
            if (shorterMAs[i - 1] <= longerMAs[i - 1] && shorterMAs[i] > longerMAs[i]) {
                return true;
            }
        }
        return false;
    }


    public record MAStrategyData(Candle[] candles, int shortPeriod, int longPeriod, int extraCandles) {
    }
}
