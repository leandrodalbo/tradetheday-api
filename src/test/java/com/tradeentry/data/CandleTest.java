package com.tradeentry.data;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CandleTest {

    @Test
    void willHaveAFactoryMethod() {
        assertThat(Candle.of(1.0f, 2.0f, 3.0f, 5.0f, 5L)).isExactlyInstanceOf(Candle.class);
    }

    @Test
    void willDefineEquals() {
        Candle c1 = new Candle(1.0f, 2.0f, 3.0f, 5.0f, 5L);
        Candle c2 = new Candle(1.0f, 2.0f, 3.0f, 5.0f, 5L);

        assertThat(c1.equals(c2)).isTrue();
    }
}
