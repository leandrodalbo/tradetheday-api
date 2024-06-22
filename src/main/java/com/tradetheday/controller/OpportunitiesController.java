package com.tradetheday.controller;

import com.tradetheday.model.Opportunity;
import com.tradetheday.model.Timeframe;
import com.tradetheday.service.OpportunitiesSearch;
import com.tradetheday.service.OpportunitiesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Set;

@RestController
@RequestMapping("tradetheday/crypto")
public class OpportunitiesController {

    private final OpportunitiesService opportunitiesService;

    private final Set<OpportunitiesSearch> searchingServices;

    public OpportunitiesController(OpportunitiesService opportunitiesService, Set<OpportunitiesSearch> searchingServices) {
        this.opportunitiesService = opportunitiesService;
        this.searchingServices = searchingServices;
    }

    @GetMapping("/opportunities/{timeframe}")
    public Flux<Opportunity> getOpportunities(@PathVariable Timeframe timeframe) {
        return opportunitiesService.findByTimeframe(timeframe);
    }

    @GetMapping("/search/bullish/engulfing/{timeframe}")
    public String searchEngulfingCandles(@PathVariable Timeframe timeframe) {
        searchingServices.forEach(search -> search.searchEngulfingCandles(timeframe));
        return "searching Engulfing Candles";
    }

    @GetMapping("/search/bullish/macrossover/{timeframe}")
    public String searchMACrossovers(@PathVariable Timeframe timeframe) {
        searchingServices.forEach(search -> search.searchMACrossOver(timeframe));
        return "searching MA Crossovers";
    }

}
