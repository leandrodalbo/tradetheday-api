package com.tradetheday.exchanging.kraken;

public enum KrakenBuySell {
    BUY("buy"),
    SELL("sell");

    private final String buysell;

    KrakenBuySell(String buysell) {
        this.buysell = buysell;
    }

    public String getBuysell() {
        return buysell;
    }
}
