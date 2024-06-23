package com.tradetheday.strategy;

import com.tradetheday.exchanging.Candle;
import org.springframework.stereotype.Component;

@Component
public class EngulfingCandleStrategy implements Strategy<Candle[]> {
    public Boolean isOn(Candle[] candles) {
        if (candles.length == 0 || candles[0] == null || candles[1] == null || candles[2] == null)
            return false;

        Candle preprev = candles[0];
        Candle prev = candles[1];
        Candle latest = candles[2];

        if (preprev.close() < preprev.open() && prev.close() > prev.open() && prev.open() <= preprev.close() && prev.close() >= preprev.open())
            return true;

        return (prev.close() < prev.open() && latest.close() > latest.open() && latest.open() <= prev.close() && latest.close() >= prev.open());
    }
}
