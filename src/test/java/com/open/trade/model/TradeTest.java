package com.open.trade.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TradeTest {

    @Test
    void willHaveAFactoryMethod() {
        assertThat(Trade.of(
                "BTCUSDT",
                0.1,
                3.4,
                3.2,
                TradeStatus.OPEN,
                false
        )).isExactlyInstanceOf(Trade.class);
    }

}
