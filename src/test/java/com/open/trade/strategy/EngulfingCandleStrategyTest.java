package com.open.trade.strategy;

import com.open.trade.data.Candle;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EngulfingCandleStrategyTest {

    EngulfingCandleStrategy strategy = new EngulfingCandleStrategy();

    @Test
    void shouldFindAnEngulfingPatternBetweenTheFirstASecond() {
        Candle[] candles = new Candle[]{
                Candle.of(1.5f, 1.8f, 0.5f, 1.0f),
                Candle.of(0.9f, 5.0f, 0.8f, 2.5f),
                Candle.of(0.9f, 5.0f, 0.8f, 2.5f),
        };
        assertThat(strategy.isEngulfing(candles)).isTrue();
    }

    @Test
    void shouldFindAnEngulfingPatternBetweenTheSecondAndLast() {
        Candle[] candles = new Candle[]{
                Candle.of(2.1f, 2.3f, 1f, 1.20f),
                Candle.of(1.5f, 1.8f, 0.5f, 1.0f),
                Candle.of(0.9f, 5.0f, 0.8f, 2.5f),
        };
        assertThat(strategy.isEngulfing(candles)).isTrue();
    }

    @Test
    void shouldBeFalseForNullCandles() {
        assertThat(strategy.isEngulfing(new Candle[2])).isFalse();
    }


}
