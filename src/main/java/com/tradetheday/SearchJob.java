package com.tradetheday;

import com.tradetheday.configuration.BinanceProps;
import com.tradetheday.configuration.KrakenProps;
import com.tradetheday.model.Timeframe;
import com.tradetheday.service.BinanceSearchService;
import com.tradetheday.service.KrakenSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SearchJob {

    private final Logger logger = LoggerFactory.getLogger(SearchJob.class);

    @Autowired
    KrakenProps krakenProps;

    @Autowired
    BinanceProps binanceProps;

    @Autowired
    KrakenSearchService krakenService;

    @Autowired
    BinanceSearchService binanceService;

    @Scheduled(cron = "0 0 0/1 * * *")
    public void searchData() {
        Thread binanceThread = new Thread(() -> binanceProps.symbols().forEach(
                binanceSymbol -> {
                    try {
                        findOnBinance(binanceSymbol, Timeframe.H1);
                        Thread.sleep(1000);
                        findOnBinance(binanceSymbol, Timeframe.H4);
                        Thread.sleep(2000);
                        findOnBinance(binanceSymbol, Timeframe.D1);
                        Thread.sleep(3000);

                    } catch (Exception e) {
                        logger.info(e.getMessage());
                    }
                }
        ));

        Thread krakenThread = new Thread(() -> krakenProps.symbols().forEach(
                krakenSymbol -> {
                    try {
                        findOnKraken(krakenSymbol, Timeframe.H1);
                        Thread.sleep(1000);
                        findOnKraken(krakenSymbol, Timeframe.H4);
                        Thread.sleep(2000);
                        findOnKraken(krakenSymbol, Timeframe.D1);
                        Thread.sleep(3000);

                    } catch (Exception e) {
                        logger.info(e.getMessage());
                    }
                }
        ));

        binanceThread.start();
        krakenThread.start();
    }


    private void findOnKraken(String symbol, Timeframe timeframe) {

        Thread engulfing = new Thread(() ->
                krakenService.searchEngulfingCandles(symbol, timeframe, krakenProps));

        logger.info(String.format("Searching Kraken Engulfing Candles: %s, %s", symbol, timeframe));
        engulfing.start();
        logger.info(String.format("Searching Kraken Engulfing Candles Finished: %s, %s", symbol, timeframe));

        Thread maCross = new Thread(() ->
                krakenService.searchMACrossOver(symbol, timeframe, krakenProps));


        logger.info(String.format("Searching Kraken MAs: %s, %s", symbol, timeframe));
        maCross.start();
        logger.info(String.format("Searching Kraken MAs Finished: %s, %s", symbol, timeframe));

    }

    private void findOnBinance(String symbol, Timeframe timeframe) {

        Thread engulfing = new Thread(() ->
                binanceService.searchEngulfingCandles(symbol, timeframe, binanceProps));

        logger.info(String.format("Searching Binance Engulfing Candles: %s, %s", symbol, timeframe));
        engulfing.start();
        logger.info(String.format("Searching Binance Engulfing Candles Finished: %s, %s", symbol, timeframe));

        Thread maCross = new Thread(() ->
                binanceService.searchMACrossOver(symbol, timeframe, binanceProps));


        logger.info(String.format("Searching Binance MAs: %s, %s", symbol, timeframe));
        maCross.start();
        logger.info(String.format("Searching Binance MAs Finished: %s, %s", symbol, timeframe));

    }

}
