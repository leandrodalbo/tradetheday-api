package com.open.trade.scheduled;

import com.open.trade.model.Speed;
import com.open.trade.service.EngulfingBinanceTrades;
import com.open.trade.service.EngulfingKrakenTrades;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class FetchEngulfinEntries {
    private final Logger logger = LoggerFactory.getLogger(FetchEngulfinEntries.class);

    private final EngulfingBinanceTrades binanceTrades;

    private final EngulfingKrakenTrades krakenTrades;

    public FetchEngulfinEntries(EngulfingBinanceTrades binanceTrades, EngulfingKrakenTrades krakenTrades) {
        this.binanceTrades = binanceTrades;
        this.krakenTrades = krakenTrades;
    }

    @Scheduled(cron = "0 */30 * * * *")
    public void fetchHighSpeedTrades() {
        logger.info("Fetching High Speed trades");
        binanceTrades.searchEntries(Speed.HIGH);
        krakenTrades.searchEntries(Speed.HIGH);
        logger.info("Fetching High Speed trades finished");
    }


    @Scheduled(cron = "0 0 */1 * * *")
    public void fetchMediumSpeedTrades() {
        logger.info("Fetching Medium Speed trades");
        binanceTrades.searchEntries(Speed.MEDIUM);
        krakenTrades.searchEntries(Speed.MEDIUM);
        logger.info("Fetching Medium Speed trades finished");
    }


    @Scheduled(cron = "0 0 */2 * * *")
    public void fetchLowSpeedTrades() {
        logger.info("Fetching Low Speed trades");
        binanceTrades.searchEntries(Speed.LOW);
        krakenTrades.searchEntries(Speed.LOW);
        logger.info("Fetching Low Speed trades");
    }
}
