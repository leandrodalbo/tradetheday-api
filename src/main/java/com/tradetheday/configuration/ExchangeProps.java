package com.tradetheday.configuration;

import java.util.Set;

public interface ExchangeProps {
    String apiUri();

    Float stop();

    Float profit();

    Set<String> symbols();
}
