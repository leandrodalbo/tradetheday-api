package com.open.trade.exchangecall;

import com.open.trade.data.Candle;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ExchangeCallTest {

    @Test
    void willReturnAnArrayOfTwoCandles() {
        List<Object> data = List.of(
                String.valueOf(Instant.now().getEpochSecond()),
                "23.0",
                "23.0",
                "23.0",
                "23.0"
        );


        assertThat(ExchangeCall.engulfingToArray(List.of(data, data)))
                .isEqualTo(
                        new Candle[]{
                                Candle.of(23.0f,
                                        23.0f,
                                        23.0f,
                                        23.0f),
                                Candle.of(23.0f,
                                        23.0f,
                                        23.0f,
                                        23.0f)
                        }
                );
    }
}
