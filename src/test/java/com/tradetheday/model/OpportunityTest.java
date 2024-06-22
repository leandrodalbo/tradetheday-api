package com.tradetheday.model;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class OpportunityTest {

    @Test
    void willHaveAFactoryMethod() {
        assertThat(
                Opportunity.of(
                        "BTCUSD",
                        Timeframe.H1,
                        true,
                        true,
                        Optional.empty(),
                        Optional.of(true),
                        Optional.of(Instant.now().getEpochSecond()),
                        Optional.of(true),
                        Optional.of(Instant.now().getEpochSecond()),
                        Optional.of(0f),
                        Optional.of(0f),
                        Optional.of(0f)

                )
        ).isExactlyInstanceOf(Opportunity.class);
    }

    @Test
    void willGenerateSymbolAndSpeedValue() {
        assertThat(Opportunity.generateId(
                "BTCUSDT",
                Timeframe.H1

        )).isEqualTo("BTCUSDT-" + Timeframe.H1);
    }
}
