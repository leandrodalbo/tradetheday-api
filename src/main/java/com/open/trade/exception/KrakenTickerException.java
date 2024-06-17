package com.open.trade.exception;

public class KrakenTickerException extends Exception {
    public KrakenTickerException() {
        super("Kraken ticker failed");
    }
}
