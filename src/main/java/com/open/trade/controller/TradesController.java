package com.open.trade.controller;

import com.open.trade.model.Trade;
import com.open.trade.model.TradeStatus;
import com.open.trade.service.TradesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("opentrade/crypto")
public class TradesController {

    private final TradesService service;

    public TradesController(TradesService service) {
        this.service = service;
    }

    @GetMapping("/trades/{status}")
    public Flux<Trade> findByStatus(@PathVariable TradeStatus status) {
        return service.findByStatus(status);
    }

}
