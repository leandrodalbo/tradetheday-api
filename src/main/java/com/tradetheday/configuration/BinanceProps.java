package com.tradetheday.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Set;

@ConfigurationProperties(prefix = "binance")
public record BinanceProps(String apiUri,
                           Float profit,
                           Float stop,
                           Set<String> symbols,
                           Integer shortMA,
                           Integer longMA,
                           Integer extraCandles
) implements ExchangeProps {
}
