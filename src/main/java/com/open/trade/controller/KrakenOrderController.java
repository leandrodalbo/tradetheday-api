package com.open.trade.controller;

import com.open.trade.exchanging.kraken.KrakenMarketBuy;
import com.open.trade.exchanging.kraken.KrakenStopLoss;
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

    @PostMapping("/kraken/enter")
    public Mono<String> newTrade(@RequestBody KrakenMarketBuy trade) {
        return orderService.dareToEnter(trade);
    }

    @PostMapping("/kraken/stop")
    public Mono<String> setStop(@RequestBody KrakenStopLoss stopLoss) {
        return orderService.setStopLoss(stopLoss);
    }

}
