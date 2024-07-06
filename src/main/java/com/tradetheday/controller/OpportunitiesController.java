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

}
