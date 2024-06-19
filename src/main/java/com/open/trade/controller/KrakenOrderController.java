package com.open.trade.controller;

import com.open.trade.exchanging.OpenTrade;
import com.open.trade.service.KrakenOrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("opentrade/crypto")
public class KrakenOrderController {

    private final KrakenOrderService orderService;

    public KrakenOrderController(KrakenOrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/kraken/neworder")
    public Mono<Object> newTrade(@RequestBody OpenTrade openTrade) {
        return orderService.newTrade(openTrade);
    }

}
