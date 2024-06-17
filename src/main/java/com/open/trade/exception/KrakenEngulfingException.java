package com.open.trade.exception;

public class KrakenEngulfingException extends Exception {
    public KrakenEngulfingException() {
        super("fetch Kraken engulfing candles failed");
    }
}
