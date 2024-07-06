package com.tradetheday.controller;

import com.tradetheday.exchanging.kraken.KrakenConditionalOrderData;
import com.tradetheday.exchanging.kraken.KrakenMarketBuy;
import com.tradetheday.service.KrakenOrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("tradetheday/crypto")
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
    public Mono<String> setStop(@RequestBody KrakenConditionalOrderData stopLoss) {
        return orderService.setStopLoss(stopLoss);
    }

    @PostMapping("/kraken/profit")
    public Mono<String> setTakeProfit(@RequestBody KrakenConditionalOrderData takeProfit) {
        return orderService.setStopLoss(takeProfit);
    }


}
