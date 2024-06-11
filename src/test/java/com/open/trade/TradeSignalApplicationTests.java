package com.open.trade;

import com.open.trade.entries.BullishEngulfingCandleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

@SpringBootTest
class TradeSignalApplicationTests {

    @Autowired
    BullishEngulfingCandleService service;


    @Test
    void contextLoads() {

        service.engulfingCandles(Set.of("BTCUSDT"), "1h");
    }

}
