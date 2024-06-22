package com.tradetheday.controller;

import com.tradetheday.model.Opportunity;
import com.tradetheday.model.Timeframe;
import com.tradetheday.service.BinanceSearchService;
import com.tradetheday.service.KrakenSearchService;
import com.tradetheday.service.OpportunitiesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("tradetheday/crypto")
public class OpportunitiesController {

    private final OpportunitiesService opportunitiesService;

    private final KrakenSearchService krakenSearch;

    private final BinanceSearchService binanceSearch;

    public OpportunitiesController(OpportunitiesService opportunitiesService, KrakenSearchService krakenSearch, BinanceSearchService binanceSearch) {
        this.opportunitiesService = opportunitiesService;
        this.krakenSearch = krakenSearch;
        this.binanceSearch = binanceSearch;
    }

    @GetMapping("/opportunities/{timeframe}")
    public Flux<Opportunity> getOpportunities(@PathVariable Timeframe timeframe) {
        return opportunitiesService.findByTimeframe(timeframe);
    }

    @GetMapping("/search/binance/ma")
    public String SearchBinanceMAs() {
        for (Timeframe tf : Timeframe.values())
            binanceSearch.searchMACrossOver(tf);

        return "searching-new-binance-ma-crossovers";
    }

    @GetMapping("/search/kraken/ma")
    public String searchKrakenMAs() {
        for (Timeframe tf : Timeframe.values())
            krakenSearch.searchMACrossOver(tf);

        return "searching-new-kraken-ma-crossovers";
    }


    @GetMapping("/search/binance/engulfing")
    public String SearchBinanceEngulfingCandles() {
        for (Timeframe tf : Timeframe.values())
            binanceSearch.searchEngulfingCandles(tf);

        return "searching-new-binance-engulfing-candles";
    }

    @GetMapping("/search/kraken/engulfing")
    public String searchKrakenEngulfinCandles() {
        for (Timeframe tf : Timeframe.values())
            krakenSearch.searchEngulfingCandles(tf);

        return "searching-new-kraken-engulfing-candles";
    }
}
