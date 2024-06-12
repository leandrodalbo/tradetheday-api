package com.open.trade.service.strategy;

import com.open.trade.data.Candle;
import com.open.trade.strategy.EngulfingCandleStrategy;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EngulfingCandleServiceTest {

    EngulfingCandleStrategy service = new EngulfingCandleStrategy();

    @Test
    void shouldFindAnEngulfingPattern() {
        Candle[] candles = new Candle[]{
                Candle.of(1.5f, 1.8f, 0.5f, 1.0f),
                Candle.of(0.9f, 5.0f, 4.5f, 2.5f)
        };
        assertThat(service.isEngulfing(candles)).isTrue();
    }


    @Test
    void shouldBeFalseWhenClosedBelow() {
        Candle[] candles = new Candle[]{
                Candle.of(1.5f, 1.8f, 0.5f, 1.0f),
                Candle.of(0.9f, 5.0f, 4.5f, 1.4f)
        };
        assertThat(service.isEngulfing(candles)).isFalse();
    }

    @Test
    void shouldBeFalseWhenThePreviousIsBullish() {
        Candle[] candles = new Candle[]{
                Candle.of(1.5f, 2.8f, 1.5f, 1.6f),
                Candle.of(0.9f, 5.0f, 4.5f, 1.4f)
        };
        assertThat(service.isEngulfing(candles)).isFalse();
    }

    @Test
    void shouldBeFalseWhenTheCurrentHasNotOpenedBelow() {
        Candle[] candles = new Candle[]{
                Candle.of(1.5f, 1.8f, 0.5f, 1.0f),
                Candle.of(1.5f, 5.0f, 4.5f, 2.5f)
        };
        assertThat(service.isEngulfing(candles)).isFalse();
    }


}
