package com.open.trade.model;

public enum TradeStatus {
    OPEN("OPEN"), CLOSED("CLOSE");

    private final String status;

    TradeStatus(String status) {
        this.status = status;
    }
}
