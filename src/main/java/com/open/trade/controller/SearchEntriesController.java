package com.open.trade.controller;

import com.open.trade.service.SearchEntriesService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("crypto")
public class SearchEntriesController {


    private final SearchEntriesService service;

    public SearchEntriesController(SearchEntriesService service) {

        this.service = service;
    }

//    @GetMapping("/engulfing/{timeFrame}")
//    public List<EngulfingDto> findByTimeFrame(@PathVariable String timeFrame) {
//        return service.engulfingCandles(this.projectSymbols.symbols(), timeFrame);
//    }

}
