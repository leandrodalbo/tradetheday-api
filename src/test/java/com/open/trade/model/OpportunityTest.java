package com.open.trade.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OpportunityTest {

    @Test
    void willHaveAFactoryMethod() {
        assertThat(Opportunity.of(
                "BTCUSDT",
                Speed.HIGH,
                true,
                3000.00F,
                3000.00F,
                3000.00F,
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
                Speed.HIGH

        )).isEqualTo("BTCUSDT-" + Speed.HIGH);
    }
}
