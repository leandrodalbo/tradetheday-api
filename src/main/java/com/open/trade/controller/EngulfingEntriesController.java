package com.open.trade.controller;

import com.open.trade.model.Opportunity;
import com.open.trade.service.OpportunitiesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("opentrade/crypto")
public class EngulfingEntriesController {

    private final OpportunitiesService opportunitiesService;

    public EngulfingEntriesController(OpportunitiesService opportunitiesService) {
        this.opportunitiesService = opportunitiesService;
    }

    @GetMapping("/engulfing/entries")
    public Flux<Opportunity> getLatestEntries() {
        return opportunitiesService.findLatestEntries();
    }

}
