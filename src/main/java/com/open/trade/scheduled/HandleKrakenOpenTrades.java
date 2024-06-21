package com.open.trade.scheduled;

import com.open.trade.exchanging.kraken.KrakenBuySell;
import com.open.trade.exchanging.kraken.KrakenOrderType;
import com.open.trade.model.Trade;
import com.open.trade.model.TradeResult;
import com.open.trade.model.TradeStatus;
import com.open.trade.repository.TradeRepository;
import com.open.trade.service.KrakenOrderService;
import com.open.trade.service.KrakenPriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class HandleKrakenOpenTrades {
    private final Logger logger = LoggerFactory.getLogger(HandleKrakenOpenTrades.class);

    private final KrakenPriceService priceService;

    private final KrakenOrderService orderService;

    private final TradeRepository tradeRepository;

    public HandleKrakenOpenTrades(KrakenPriceService priceService, KrakenOrderService orderService, TradeRepository tradeRepository) {
        this.priceService = priceService;
        this.orderService = orderService;
        this.tradeRepository = tradeRepository;
    }


    public void handleOpenTrades() {
        logger.info("Checking Open Trades");

        tradeRepository.findByTradestatus(TradeStatus.OPEN)
                .filter(Trade::isakrakentrade)
                .subscribe(it -> {
                    priceService.latestPrice(it.symbol())
                            .subscribe(price -> {
                                if (price <= it.stopprice()) {
                                    logger.info(String.format("stop-loss for symbol, %s", it.symbol()));
                                    closeTrade(it, TradeResult.FAILED);
                                    logger.info(String.format("%s Trade Closed", it.symbol()));
                                }

                                if (price >= it.profitprice()) {
                                    logger.info(String.format("take-profit for symbol, %s", it.symbol()));
                                    closeTrade(it, TradeResult.SUCCESS);
                                    logger.info(String.format("%s Trade Closed", it.symbol()));
                                }

                                logger.info(String.format("%s latest-Price: %s, trade-price: %s, take-profit: %s, stop-loss: %s", it.symbol(), price, it.price(), it.profitprice(), it.stopprice()));

                            });
                });
    }

    private void closeTrade(Trade trade, TradeResult result) {
        orderService.postOrder(trade.symbol(), trade.volume(), KrakenBuySell.SELL, KrakenOrderType.MARKET, Optional.empty())
                .subscribe(closing -> {
                    if (closing.success()) {
                        tradeRepository.save(new Trade(
                                trade.id(),
                                trade.symbol(),
                                trade.volume(),
                                trade.price(),
                                trade.profitprice(),
                                trade.stopprice(),
                                TradeStatus.CLOSED,
                                trade.ondatetime(),
                                result,
                                trade.isakrakentrade(),
                                trade.version()
                        )).subscribe();

                        logger.info(String.format("%s-trade-%s", trade.symbol(), result.toString()));
                    } else {
                        logger.warn(closing.message());
                    }
                });
    }

}
