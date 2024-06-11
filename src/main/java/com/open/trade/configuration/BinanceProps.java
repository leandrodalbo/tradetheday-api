package com.open.trade.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Set;

@ConfigurationProperties(prefix = "binance")
public record BinanceProps(String binanceUri, Float profit, Float stop, Set<String> symbols) {
}
