package com.tradetheday.strategy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tradetheday.exchanging.Candle;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class MACandleStrategyTest {

    private final ObjectMapper mapper = new ObjectMapper();
    MACandleStrategy strategy = new MACandleStrategy();


    @Test
    void shouldFindMACrossOvers() {

        Candle[] bearishCandles = loadData("src/test/resources/binancebearishMAdata.json");
        Candle[] bullishCandles = loadData("src/test/resources/binancebullishMAdata.json");

        assertThat(strategy.isOn(new MACandleStrategy.MAStrategyData(
                bearishCandles,
                9,
                21,
                15
        ))).isFalse();

        assertThat(strategy.isOn(new MACandleStrategy.MAStrategyData(
                bullishCandles,
                9,
                21,
                15
        ))).isTrue();

    }

    private Candle[] toCandlesArray(List values) {
        if (values == null) {
            return new Candle[0];
        }

        Candle[] result = new Candle[values.size()];

        for (int i = 0; i < values.size(); i++) {
            List candle = (List) values.get(i);

            result[i] = Candle.of(
                    Float.parseFloat((String) candle.get(1)),
                    Float.parseFloat((String) candle.get(2)),
                    Float.parseFloat((String) candle.get(3)),
                    Float.parseFloat((String) candle.get(4)));

        }

        return result;
    }

    private Candle[] loadData(String file) {
        List data = null;

        try {
            data = mapper.readValue(new File(file), List.class);
        } catch (IOException e) {
            fail(e.getMessage());
        }
        return toCandlesArray(data);
    }
}
