package com.open.trade;

import com.open.trade.call.BinanceCall;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TradeSignalApplicationTests {

    @Autowired
    BinanceCall binanceCall;

    @Test
    void contextLoads() {

        binanceCall.searchEngulfingEntries("BTCUSDT", "1h");


    }

}
