package my.trade.controller;

import my.trade.entries.BullishEngulfingCandleService;
import my.trade.configuration.ProjectProps;
import my.trade.dto.EngulfingDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("crypto")
public class EngulfingCandlesController {


    private final ProjectProps projectProps;
    private final BullishEngulfingCandleService service;

    public EngulfingCandlesController(ProjectProps projectProps, BullishEngulfingCandleService service) {
        this.projectProps = projectProps;
        this.service = service;
    }

    @GetMapping("/engulfing/{timeFrame}")
    public List<EngulfingDto> findByTimeFrame(@PathVariable String timeFrame) {
        return service.engulfingCandles(this.projectProps.symbols(), timeFrame);
    }

}
