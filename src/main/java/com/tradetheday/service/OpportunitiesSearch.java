package com.tradetheday.service;

import com.tradetheday.model.Timeframe;

public interface OpportunitiesSearch {
    void searchEngulfingCandles(Timeframe interval);

    void searchMACrossOver(Timeframe interval);
}
