package com.open.trade.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "project")
public record ProjectProps(String binanceUri, Float profit, Float stop) {
}
