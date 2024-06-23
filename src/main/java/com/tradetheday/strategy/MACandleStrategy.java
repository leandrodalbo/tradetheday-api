package com.tradetheday.strategy;


import com.tradetheday.exchanging.Candle;
import org.springframework.stereotype.Component;

@Component
public class MACandleStrategy implements Strategy<MACandleStrategy.MAStrategyData> {

    @Override
    public Boolean isOn(MAStrategyData data) {
        int i = data.candles.length - 1;
        int backward = 5;
        int last = i - backward;

        boolean flag = false;

        while (i >= last) {
            double longSum = 0;
            double shortSum = 0;

            int longCount = 0;
            int shortCount = 0;

            int lIndex = i;
            int sIndex = i;

            while (lIndex >= 0 && longCount < data.longPeriod) {
                longSum += data.candles[lIndex].close();
                longCount++;
                lIndex--;
            }


            while (sIndex >= 0 && shortCount < data.shortPeriod) {
                shortSum += data.candles[sIndex].close();
                shortCount++;
                sIndex--;
            }

            if (((shortSum / data.shortPeriod()) > (longSum / data.longPeriod())) &&
                    (i == (data.candles.length - 1))) {
                flag = true;
            }

            if (flag && (shortSum / data.shortPeriod()) < (longSum / data.longPeriod())) {
                return true;
            }


            i--;
        }


        return false;
    }


    public record MAStrategyData(Candle[] candles, int shortPeriod, int longPeriod) {
    }
}
