package com.tradeentry.candlePattern;

import com.tradeentry.data.Candle;
import org.springframework.stereotype.Service;

@Service
public class TwoCandleService {

    public Boolean isBullishEngulfing(Candle firstCandle, Candle secondCandle) {
        return (firstCandle.open() > firstCandle.close()) && (secondCandle.open() < secondCandle.close()) && (secondCandle.close() > firstCandle.open()) && (secondCandle.open() < firstCandle.close());
    }
}
