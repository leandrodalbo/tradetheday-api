package com.tradetheday.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OpportunityTest {

    @Test
    void willHaveAFactoryMethod() {
        assertThat(Opportunity.of(
                "BTCUSDT",
                Timeframe.H1,
                true,
                true,
                3000.00F,
                3000.00F,
                3000.00F,
                false,
                false,
                0.0f,
                0.0f,
                0.0f
        )).isExactlyInstanceOf(Opportunity.class);
    }

    @Test
    void willGenerateSymbolAndSpeedValue() {
        assertThat(Opportunity.generateSimbolSpeed(
                "BTCUSDT",
                Timeframe.H1

        )).isEqualTo("BTCUSDT-" + Timeframe.H1);
    }
}
