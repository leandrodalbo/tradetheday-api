package com.open.trade.controller;

import com.open.trade.model.Trade;
import com.open.trade.model.TradeResult;
import com.open.trade.model.TradeStatus;
import com.open.trade.service.TradesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Optional;

@RestController
@RequestMapping("opentrade/crypto")
public class TradesController {

    private final TradesService service;

    public TradesController(TradesService service) {
        this.service = service;
    }

    @GetMapping("/trades")
    public Flux<Trade> findTrades(@RequestParam Optional<TradeStatus> status, @RequestParam Optional<TradeResult> result, @RequestParam Optional<Boolean> today) {
        return service.findTrades(status, result, today);
    }
}
