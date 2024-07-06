package com.tradetheday.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Set;

@ConfigurationProperties(prefix = "kraken")
public record KrakenProps(String apiUri,
                          String apiKey,
                          String apiSecret,
                          Float profit,
                          Float stop,
                          Set<String> symbols,
                          Integer shortMA,
                          Integer longMA,
                          Integer extraCandles
) implements ExchangeProps {
}
