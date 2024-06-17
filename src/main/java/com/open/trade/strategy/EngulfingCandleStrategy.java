package com.open.trade.strategy;

import com.open.trade.data.Candle;
import org.springframework.stereotype.Component;

@Component
public class EngulfingCandleStrategy {
    public Boolean isEngulfing(Candle[] candles) {
        if (candles[0] == null || candles[1] == null || candles[2] == null)
            return false;

        Candle preprev = candles[0];
        Candle prev = candles[1];
        Candle current = candles[2];

        if (((preprev.open() > preprev.close()) && (prev.low() <= preprev.close()) && prev.close() >= preprev.open()))
            return true;

        return ((prev.open() > prev.close()) && (current.low() <= prev.close()) && current.close() >= prev.open());
    }
}
