package com.open.trade.exchanging.kraken;

public enum KrakenBuySell {
    BUY("buy"),
    SELL("sell");

    private final String buysell;

    KrakenBuySell(String buysell) {
        this.buysell = buysell;
    }
}