package com.tradetheday.exchanging.kraken;

public enum KrakenOrderType {
    MARKET("market"),
    STOP_LOSS("stop-loss"),
    TAKE_PROFIT("take-profit");

    private final String ordertype;

    KrakenOrderType(String ordertype) {
        this.ordertype = ordertype;
    }

    public String getOrdertype() {
        return ordertype;
    }
}
