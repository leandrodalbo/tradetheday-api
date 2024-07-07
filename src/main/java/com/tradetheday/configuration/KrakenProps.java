package com.tradetheday.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Set;

@ConfigurationProperties(prefix = "kraken")
public record KrakenProps(String apiuri,
                          String apikey,
                          String apisecret,
                          Float profit,
                          Float stop,
                          Set<String> symbols,
                          Integer shortma,
                          Integer longma,
                          Integer extracandles
) implements ExchangeProps {
}
