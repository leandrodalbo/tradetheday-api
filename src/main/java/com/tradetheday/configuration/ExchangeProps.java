package com.tradetheday.configuration;

import java.util.Set;

public interface ExchangeProps {
    String apiuri();

    Float stop();

    Float profit();

    Set<String> symbols();
}
