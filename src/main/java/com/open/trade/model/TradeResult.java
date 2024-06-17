package com.open.trade.model;

public enum TradeResult {
    SUCCESS("SUCCESS"), FAILED("FAILED");

    private final String result;

    TradeResult(String result) {
        this.result = result;
    }
}
