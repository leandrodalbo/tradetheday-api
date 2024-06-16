package com.open.trade.controller;

import com.open.trade.data.OpenTrade;
import com.open.trade.model.Trade;
import com.open.trade.service.KrakenOrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("opentrade/crypto")
public class KrakenOrderControllerController {

    private final KrakenOrderService orderService;

    public KrakenOrderControllerController(KrakenOrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/kraken/neworder}")
    public Mono<Trade> findByEngulfingBySpeed(@RequestBody OpenTrade openTrade) {
        return orderService.newTrade(openTrade);
    }

}
