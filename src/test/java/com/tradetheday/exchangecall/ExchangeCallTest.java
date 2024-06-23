package com.tradetheday.exchangecall;

import com.tradetheday.exchanging.Candle;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ExchangeCallTest {

    @Test
    void willReturnAnArrayOfThreeCandles() {
        List<Object> c0 = List.of(
                String.valueOf(Instant.now().getEpochSecond()),
                "23.0",
                "24.0",
                "25.0",
                "26.0"
        );

        List<Object> c1 = List.of(
                String.valueOf(Instant.now().getEpochSecond()),
                "27.0",
                "28.0",
                "29.0",
                "30.0"
        );
        assertThat(ExchangeCall.toCandlesArray(List.of(c0, c1)))
                .isEqualTo(
                        new Candle[]{
                                Candle.of(23.0f,
                                        24.0f,
                                        25.0f,
                                        26.0f),
                                Candle.of(27.0f,
                                        28.0f,
                                        29.0f,
                                        30.0f)
                        }
                );
    }

    @Test
    void willReturnAndArrayOfNullValues() {
        assertThat(ExchangeCall.toCandlesArray(null)).isEqualTo(new Candle[0]);
        assertThat(ExchangeCall.toCandlesArray(List.of())).isEqualTo(new Candle[0]);
    }
}
