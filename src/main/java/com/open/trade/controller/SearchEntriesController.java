package com.open.trade.controller;

import com.open.trade.configuration.ProjectSymbols;
import com.open.trade.service.SearchEntriesService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("crypto")
public class SearchEntriesController {


    private final ProjectSymbols projectSymbols;
    private final SearchEntriesService service;

    public SearchEntriesController(ProjectSymbols projectSymbols, SearchEntriesService service) {
        this.projectSymbols = projectSymbols;
        this.service = service;
    }

//    @GetMapping("/engulfing/{timeFrame}")
//    public List<EngulfingDto> findByTimeFrame(@PathVariable String timeFrame) {
//        return service.engulfingCandles(this.projectSymbols.symbols(), timeFrame);
//    }

}
