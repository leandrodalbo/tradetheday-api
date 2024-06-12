package com.open.trade.call;

import com.open.trade.configuration.BinanceProps;
import com.open.trade.data.Candle;
import com.open.trade.model.Opportunity;
import com.open.trade.repository.OpportunityRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
public class BinanceCall extends ExchangeCall {
    private static final String KLINES = "/api/v3/klines";

    private final int CANDLES_LIMIT = 2;
    private final BinanceProps props;


    public BinanceCall(WebClient.Builder builder, OpportunityRepository repository, BinanceProps props) {
        super(builder.baseUrl(props.binanceUri()).build(), repository);
        this.props = props;
    }

    @Override
    public void searchEngulfingEntries(String interval) {
        this.props.symbols().forEach(symbol -> {
            saveInfo(symbol, this.fetchData(symbol, interval, CANDLES_LIMIT), interval);
        });
    }


    @Override
    protected void saveInfo(String symbol, List data, String interval) {
        Candle[] twoCandles = candles(data);
        if (isEngulfing(twoCandles)) {
            Candle c1 = twoCandles[1];
            repository.save(Opportunity.of(
                    symbol,
                    interval,
                    true,
                    c1.close(),
                    c1.close() * props.stop(),
                    c1.close() * props.profit(),
                    0L,
                    false,
                    0f,
                    0f,
                    0f
            ));
        }
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
}
