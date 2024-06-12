package com.open.trade;

import com.open.trade.exchangecall.BinanceCall;
import com.open.trade.exchangecall.KrakenCall;
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
        System.out.println("ddd");
    }

}
