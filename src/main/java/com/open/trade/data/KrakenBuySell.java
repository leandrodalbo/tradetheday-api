package com.open.trade.data;

public enum KrakenBuySell {
    BUY("buy"),
    SELL("sell");

    private final String buysell;

    KrakenBuySell(String buysell) {
        this.buysell = buysell;
    }
}
