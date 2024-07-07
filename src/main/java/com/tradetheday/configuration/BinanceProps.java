package com.tradetheday.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Set;

@ConfigurationProperties(prefix = "binance")
public record BinanceProps(String apiuri,
                           Float profit,
                           Float stop,
                           Set<String> symbols,
                           Integer shortma,
                           Integer longma,
                           Integer extracandles
) implements ExchangeProps {
}
