package com.open.trade.call;

import com.open.trade.configuration.ProjectProps;
import com.open.trade.data.Candle;
import com.open.trade.model.TradeOneHour;
import com.open.trade.repository.OneHourRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

@Component
public class BinanceCall extends ExchangeCall {
    private static final String KLINES = "/api/v3/klines";

    private final int CANDLES_LIMIT = 2;
    private final ProjectProps props;

    private OneHourRepository repository;

    public BinanceCall(WebClient binanceClient, ProjectProps props) {
        super(binanceClient);
        this.props = props;
    }

    @Override
    public void searchEngulfingEntries(String symbol, String interval) {
        List candles = this.fetchData(symbol, interval, CANDLES_LIMIT);
        saveInfo(symbol, candles);
    }

    private List fetchData(String symbol, String interval, Integer limit) {
        return client.get()
                .uri(
                        builder -> builder.path(KLINES)
                                .queryParam("symbol", symbol)
                                .queryParam("interval", interval)
                                .queryParam("limit", limit)
                                .build())
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(List.class)
                .block();
    }

    private void saveInfo(String symbol, List data) {
        Candle c0 = fromList((List) data.get(0));
        Candle c1 = fromList((List) data.get(1));

        if (isEngulfing(c0, c1)) {

            Optional<TradeOneHour> found = repository.findById(symbol);

            if (found.isEmpty()) {
                repository.save(new TradeOneHour(
                        symbol,
                        true,
                        c1.close(),
                        props.stop() * c1.close(),
                        props.profit() * c1.close(),
                        null,
                        null,
                        null,
                        null,
                        null
                ));
            } else {
                TradeOneHour info = found.get();
                repository.save(new TradeOneHour(
                        info.symbol(),
                        true,
                        c1.close(),
                        props.stop() * c1.close(),
                        props.profit() * c1.close(),
                        info.krakenEntry(),
                        info.krakenPrice(),
                        info.krakenStop(),
                        info.krakenProfit(),
                        info.version()
                ));
            }

        }
    }

    private Candle fromList(List<Object> values) {
        return Candle.of(
                Float.parseFloat((String) values.get(1)),
                Float.parseFloat((String) values.get(2)),
                Float.parseFloat((String) values.get(3)),
                Float.parseFloat((String) values.get(4)));
    }


}
