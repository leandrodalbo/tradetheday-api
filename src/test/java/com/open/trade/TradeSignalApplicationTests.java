package com.open.trade;

import com.open.trade.call.BinanceCall;
import com.open.trade.call.KrakenCall;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TradeSignalApplicationTests {

    @Autowired
    BinanceCall binanceCall;

    @Autowired
    KrakenCall krakenCall;

    @Test
    void contextLoads() {

        krakenCall.searchEngulfingEntries("60");
        binanceCall.searchEngulfingEntries("1h");


    }

}
