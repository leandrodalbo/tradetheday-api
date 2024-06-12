package com.open.trade;

import com.open.trade.service.EngulfingBinanceSearch;
import com.open.trade.service.EngulfingKrakenSearch;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TradeSignalApplicationTests {

    @Autowired
    EngulfingKrakenSearch ks;

    @Autowired
    EngulfingBinanceSearch bs;

    @Test
    void contextLoads() {
        bs.searchEntries("1d");
        ks.searchEntries("240");
    }

}
