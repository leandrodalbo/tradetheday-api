package com.tradetheday.messages;

public enum Messages {
    INVALID_KRAKEN_SYMBOL("Invalid Kraken symbol"),
    KRAKEN_ORDER_FAILED("Kraken Order Failed"),
    KRAKEN_ORDER_SUCCESS("Kraken Order Success"),
    KRAKEN_FETCH_FAILED("Kraken fetch failed for symbol"),
    TRADE_ENTRY_SEARCH("Searching Trade Entry");

    private final String message;

    Messages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
