package com.open.trade.exception;

public class KrakenOrderException extends Exception {
    public KrakenOrderException() {
        super("Kraken trade failed");
    }
}
