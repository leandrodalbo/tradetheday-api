package com.open.trade.strategy;

import com.open.trade.data.Candle;
import org.springframework.stereotype.Component;

@Component
public class EngulfingCandleStrategy {
    public Boolean isEngulfing(Candle[] candles) {
        if (candles[0] == null || candles[1] == null)
            return false;

        Candle prev = candles[0];
        Candle current = candles[1];

        return ((prev.open() > prev.close()) && (current.open() <= prev.close()) && current.close() >= prev.open());
    }
}
