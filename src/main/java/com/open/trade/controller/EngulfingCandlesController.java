package com.open.trade.controller;

import com.open.trade.configuration.ProjectSymbols;
import com.open.trade.dto.EngulfingDto;
import com.open.trade.entries.BullishEngulfingCandleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("crypto")
public class EngulfingCandlesController {


    private final ProjectSymbols projectSymbols;
    private final BullishEngulfingCandleService service;

    public EngulfingCandlesController(ProjectSymbols projectSymbols, BullishEngulfingCandleService service) {
        this.projectSymbols = projectSymbols;
        this.service = service;
    }

    @GetMapping("/engulfing/{timeFrame}")
    public List<EngulfingDto> findByTimeFrame(@PathVariable String timeFrame) {
        return service.engulfingCandles(this.projectSymbols.symbols(), timeFrame);
    }

}
