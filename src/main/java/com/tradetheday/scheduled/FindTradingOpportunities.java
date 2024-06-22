package com.tradetheday.scheduled;

import com.tradetheday.model.Timeframe;
import com.tradetheday.service.BinanceSearchService;
import com.tradetheday.service.KrakenSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class FindTradingOpportunities {
    private final Logger logger = LoggerFactory.getLogger(FindTradingOpportunities.class);

    private final KrakenSearchService krakenSearch;

    private final BinanceSearchService binanceSearch;

    public FindTradingOpportunities(KrakenSearchService krakenSearch, BinanceSearchService binanceSearch) {
        this.krakenSearch = krakenSearch;
        this.binanceSearch = binanceSearch;
    }

    @Scheduled(cron = "0 1 * * * *")
    public void searchBinanceEngulfingCandles() {
        for (Timeframe tf : Timeframe.values()) {
            logger.info(String.format("Searching Binance Engulfing candles, %s", tf));
            binanceSearch.searchEngulfingCandles(tf);
            logger.info(String.format("Searching Binance Engulfing candles, %s finished", tf));
        }
    }

    //@Scheduled(cron = "0 2 0/1 * * *")
    public void searchKrakenEngulfingCandles() {
        for (Timeframe tf : Timeframe.values()) {
            logger.info(String.format("Searching Kraken Engulfing candles, %s", tf));
            krakenSearch.searchEngulfingCandles(tf);
            logger.info(String.format("Searching Kraken Engulfing candles, %s finished", tf));
        }
    }


   // @Scheduled(cron = "0 3 0/1 * * *")
    public void searchKrakenMACrossovers() {
        for (Timeframe tf : Timeframe.values()) {
            logger.info(String.format("Searching Kraken MA Crossovers, %s", tf));
            krakenSearch.searchMACrossOver(tf);
            logger.info(String.format("Searching Kraken MA Crossovers, %s finished", tf));
        }
    }

   // @Scheduled(cron = "0 5 0/1 * * *")
    public void searchBinanceMACrossovers() {
        for (Timeframe tf : Timeframe.values()) {
            logger.info(String.format("Searching Binance MA Crossovers, %s", tf));
            binanceSearch.searchMACrossOver(tf);
            logger.info(String.format("Searching Binance MA Crossovers, %s finished", tf));
        }
    }
}
