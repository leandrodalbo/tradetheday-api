package com.open.trade.call;

import com.open.trade.call.exchange.KrakenResponse;
import com.open.trade.configuration.KrakenProps;
import com.open.trade.data.Candle;
import com.open.trade.model.Onehour;
import com.open.trade.repository.OneHourRepository;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class KrakenCall extends ExchangeCall {
    private static final String ON_PATH = "/0/public/OHLC";

    private final KrakenProps props;
    private final WebClient client;


    private OneHourRepository repository;

    public KrakenCall(WebClient.Builder builder, KrakenProps props) {
        this.props = props;
        this.client = builder.baseUrl(props.apiUri()).build();
    }

    @Override
    public void searchEngulfingEntries(String interval) {

        Map result =(Map)  this.fetchData("SOLUSDT", Integer.parseInt(interval)).result();

        List values =(List) result.get("SOLUSDT");

        saveInfo("SOLUSDT", values);
    }

    private KrakenResponse fetchData(String symbol, Integer interval) {
        long since = Instant.now().minus(2, ChronoUnit.HOURS).getEpochSecond();

        return client.get()
                .uri(
                        builder -> builder.path(ON_PATH)
                                .queryParam("pair", symbol)
                                .queryParam("interval", interval)
                                .queryParam("since", since)
                                .build()
                )
                .retrieve()
                .bodyToMono(KrakenResponse.class)
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
//
//    private String uriPath(String pair, Integer interval, long since) {
//
//        String uriPath = ON_PATH + "?pair=" +
//                pair +
//                "&interval=" +
//                interval +
//                "&since=" +
//                since;
//
//        return uriPath;
//    }
//
//    private String generateSignature(String uriPath, String nonce) {
//        try {
//            String postData = "nonce=" + nonce;
//            String message = nonce + postData;
//            byte[] decodedSecret = Base64.decodeBase64(props.apiSecret());
//
//            Mac mac = Mac.getInstance(props.macAlgorithm());
//            SecretKeySpec secretKeySpec = new SecretKeySpec(decodedSecret, props.macAlgorithm());
//            mac.init(secretKeySpec);
//
//            mac.update(uriPath.getBytes(StandardCharsets.UTF_8));
//            byte[] digest = mac.doFinal(message.getBytes(StandardCharsets.UTF_8));
//
//            return Base64.encodeBase64String(digest);
//        } catch (Exception e) {
//            throw new RuntimeException("Error generating API signature", e);
//        }
//    }
}
