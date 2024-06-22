package com.tradetheday.strategy;

import com.tradetheday.exchanging.Candle;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EngulfingCandleStrategyTest {

    EngulfingCandleStrategy strategy = new EngulfingCandleStrategy();

    @Test
    void shouldFindAnEngulfingPatternBetweenTheFirstASecond() {
        Candle[] candles = new Candle[]{
                Candle.of(2f, 2.5f, 1.2f, 1.5f),
                Candle.of(1.4f, 3.0f, 1.2f, 2.1f),
                Candle.of(0.9f, 5.0f, 0.8f, 2.5f),
        };
        assertThat(strategy.isOn(candles)).isTrue();
    }

    @Test
    void shouldFindAnEngulfingPatternBetweenTheSecondAndLast() {
        Candle[] candles = new Candle[]{
                Candle.of(1.1f, 2.2f, 1f, 1.0f),
                Candle.of(3.1f, 3.5f, 2.3f, 2.4f),
                Candle.of(2.1f, 3.4f, 1.9f, 3.1f),
        };
        assertThat(strategy.isOn(candles)).isTrue();
    }

    @Test
    void shouldBeFalseForNullCandles() {
        assertThat(strategy.isOn(new Candle[2])).isFalse();
    }


}
