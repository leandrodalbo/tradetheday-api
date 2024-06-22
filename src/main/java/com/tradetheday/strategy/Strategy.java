package com.tradetheday.strategy;

public interface Strategy<T> {
    Boolean isOn(T analyze);
}
