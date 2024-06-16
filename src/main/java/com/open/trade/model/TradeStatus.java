package com.open.trade.model;

public enum TradeStatus {
    BUY("buy"),
    SELL("sell");

    private final String buysell;

    TradeStatus(String buysell) {
        this.buysell = buysell;
    }
}
