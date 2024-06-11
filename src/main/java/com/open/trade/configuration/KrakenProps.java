package com.open.trade.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kraken")
public record KrakenProps(String apiUri, String apiKey, String apiSecret, Float profit, Float stop) {
}
