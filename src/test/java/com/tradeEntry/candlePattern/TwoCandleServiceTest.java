package com.tradeEntry.candlePattern;

import com.tradeEntry.data.Candle;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TwoCandleServiceTest {


    TwoCandleService underTest = new TwoCandleService();


    @Test
    void shouldFindAnEngulfingPattern() {
        Candle c1 = Candle.of(1.5f, 1.8f, 0.5f, 1.0f, 34L);
        Candle c2 = Candle.of(0.9f, 5.0f, 4.5f, 2.5f, 57L);

        assertThat(underTest.isBullishEngulfing(c1, c2)).isTrue();
    }

    @Test
    void shouldBeFalseWhenTheSecondCandleIsBearish() {
        Candle c1 = Candle.of(1.5f, 1.8f, 0.5f, 1.0f, 34L);
        Candle c2 = Candle.of(0.9f, 1.0f, 0.5f, 0.6f, 57L);

        assertThat(underTest.isBullishEngulfing(c1, c2)).isFalse();
    }

    @Test
    void shouldBeFalseWhenTheFirstCandleCandleIsBullish() {
        Candle c1 = Candle.of(1.5f, 2.5f, 1.0f, 2f, 34L);
        Candle c2 = Candle.of(0.9f, 1.0f, 0.5f, 0.6f, 57L);

        assertThat(underTest.isBullishEngulfing(c1, c2)).isFalse();
    }

    @Test
    void shouldBeFalseTheSecondCloseBelowTheFirst() {
        Candle c1 = Candle.of(1.5f, 1.8f, 0.5f, 1.0f, 34L);
        Candle c2 = Candle.of(0.9f, 5.0f, 1.5f, 1.5f, 57L);

        assertThat(underTest.isBullishEngulfing(c1, c2)).isFalse();
    }


    @Test
    void shouldBeFalseIfTheSecondNotOpenedBelowTheFirst() {
        Candle c1 = Candle.of(1.5f, 1.8f, 0.5f, 1.0f, 34L);
        Candle c2 = Candle.of(1.6f, 5.0f, 4.5f, 2.5f, 57L);

        assertThat(underTest.isBullishEngulfing(c1, c2)).isFalse();
    }
}
