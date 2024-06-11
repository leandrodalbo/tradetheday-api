package com.open.trade.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Set;

@ConfigurationProperties(prefix = "project")
public record ProjectSymbols(Set<String> symbols) {
}
