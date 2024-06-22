package com.tradetheday.strategy;

import com.tradetheday.exchanging.Candle;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MACandleStrategyTest {

    private final Candle[] candles = new Candle[]{
            new Candle(64232.0100f,
                    64460.1400f,
                    64099.89000f,
                    64430.000f),
            new Candle(64200.520f,
                    64208.00f,
                    63828.090f,
                    64520.000f),
            new Candle(64084.44000000f,
                    64199.70000000f,
                    64084.44000000f,
                    64289.15000f),
            new Candle(64155.02000000f,
                    64224.00000000f,
                    64072.00000000f,
                    64325.99000f),
            new Candle(64143.56000000f,
                    64185.90000000f,
                    63943.82000000f,
                    64298.53000000f),

    };

    MACandleStrategy strategy = new MACandleStrategy();

    @Test
    void shouldFindMACrossOvers() {

        assertThat(
                strategy.isOn(new MACandleStrategy.MAStrategyData(
                        candles, 3, 5
                ))).isFalse();

    }
}
