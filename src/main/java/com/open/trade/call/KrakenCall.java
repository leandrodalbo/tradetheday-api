package com.open.trade.call;

import com.open.trade.call.exchange.KrakenResponse;
import com.open.trade.configuration.KrakenProps;
import com.open.trade.data.Candle;
import com.open.trade.model.Opportunity;
import com.open.trade.repository.OpportunityRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

@Component
public class KrakenCall extends ExchangeCall {
    private static final String ON_PATH = "/0/public/OHLC";

    private final KrakenProps props;

    public KrakenCall(WebClient.Builder builder, OpportunityRepository repository, KrakenProps props) {
        super(builder.baseUrl(props.apiUri()).build(), repository);
        this.props = props;
    }

    @Override
    public void searchEngulfingEntries(String interval) {
        this.props.symbols().forEach(symbol -> {
            Map data = (Map) this.fetchData(symbol, Integer.parseInt(interval)).result();
            saveInfo(symbol, (List) data.get(symbol), interval);
        });
    }

    @Override
    protected void saveInfo(String symbol, List data, String interval) {
        Candle[] twoCandles = candles(data);

        if (isEngulfing(twoCandles)) {
            Candle c1 = twoCandles[1];
            repository.save(Opportunity.of(
                    symbol,
                    "0",
                    false,
                    0f,
                    0f,
                    0f,
                    Long.parseLong(interval),
                    true,
                    c1.close(),
                    c1.close() * props.stop(),
                    c1.close() * props.profit()

            ));
        }
    }

    private KrakenResponse fetchData(String symbol, Integer interval) {
        return client.get()
                .uri(
                        builder -> builder.path(ON_PATH)
                                .queryParam("pair", symbol)
                                .queryParam("interval", interval)
                                .queryParam("since", sinceParameter(interval))
                                .build()
                )
                .retrieve()
                .bodyToMono(KrakenResponse.class)
                .block();
    }

    private long sinceParameter(int minutes) {
        return switch (minutes) {
            case 60 -> Instant.now().minus(2, ChronoUnit.HOURS).getEpochSecond();
            case 240 -> Instant.now().minus(8, ChronoUnit.HOURS).getEpochSecond();
            default -> Instant.now().minus(2, ChronoUnit.DAYS).getEpochSecond();
        };
    }
}
