package com.open.trade.call;

import com.open.trade.configuration.BinanceProps;
import com.open.trade.data.Candle;
import com.open.trade.model.Onehour;
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
    private final BinanceProps props;

    private final WebClient client;


    private OneHourRepository repository;

    public BinanceCall(WebClient.Builder builder, BinanceProps props) {
        this.props = props;
        this.client =  builder.baseUrl(props.binanceUri()).build();
    }

    @Override
    public void searchEngulfingEntries(String interval) {
        this.props.symbols().forEach(symbol -> {
            saveInfo(symbol, this.fetchData(symbol, interval, CANDLES_LIMIT));
        });
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

            Optional<Onehour> found = repository.findById(symbol);

            if (found.isEmpty()) {
                repository.save(new Onehour(
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
                Onehour info = found.get();
                repository.save(new Onehour(
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
