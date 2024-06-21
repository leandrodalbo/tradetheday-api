package com.open.trade.service;

import com.open.trade.configuration.KrakenProps;
import com.open.trade.exchangecall.KrakenCall;
import com.open.trade.exchanging.kraken.*;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class KrakenOrderService {
    private static final String ADD_ORDER_PATH = "/0/private/AddOrder";
    private static final String SHA_256 = "SHA-256";
    private static final String HMAC_SHA_512 = "HmacSHA512";


    private final KrakenProps props;
    private final KrakenCall krakenCall;

    public KrakenOrderService(KrakenProps props, KrakenCall krakenCall) {
        this.props = props;
        this.krakenCall = krakenCall;
    }

    public Mono<String> setStopLoss(KrakenStopLoss stop) {

        if (!props.symbols().contains(stop.symbol())) {
            return Mono.just("Invalid Kraken symbol");
        }

        return postOrder(stop.symbol(), stop.volume(), KrakenBuySell.SELL, KrakenOrderType.STOP_LOSS, Optional.of(stop.trigger()))
                .flatMap(it ->
                        {
                            if (!it.success()) {
                                return Mono.just(it.message());
                            } else {
                                return Mono.just("Stop Loss completed");
                            }
                        }
                );
    }


    public Mono<String> dareToEnter(KrakenMarketBuy trade) {

        if (!props.symbols().contains(trade.symbol())) {
            return Mono.just("Invalid Kraken symbol");
        }

        return postOrder(trade.symbol(), trade.volume(), KrakenBuySell.BUY, KrakenOrderType.MARKET, Optional.empty())
                .flatMap(it ->
                        {
                            if (!it.success()) {
                                return Mono.just(it.message());
                            } else {
                                return Mono.just("Order Completed");
                            }
                        }
                );
    }

    public Mono<KrakenPostResult> postOrder(String symbol, double volume, KrakenBuySell buySell, KrakenOrderType orderType, Optional<Double> price) {
        String nonce = String.valueOf(System.currentTimeMillis());
        Map<String, String> params = new HashMap<>();

        params.put("nonce", nonce);
        params.put("ordertype", orderType.name().toLowerCase());
        params.put("pair", symbol);
        params.put("type", buySell.name().toLowerCase());
        params.put("volume", String.valueOf(volume));

        price.ifPresent(value -> params.put("price", String.valueOf(value)));

        String data = postingData(params);

        try {

            return krakenCall.postOrder(new KrakenOrderPost(
                    props.apiKey(),
                    signature(props.apiSecret(), data, nonce, ADD_ORDER_PATH),
                    data
            ));

        } catch (Exception e) {
            return Mono.just(new KrakenPostResult(false, e.getMessage()));
        }
    }

    public String signature(String privateKey, String encodedPayload, String nonce, String endpointPath) throws NoSuchAlgorithmException, InvalidKeyException {
        final byte[] pathInBytes = endpointPath.getBytes(StandardCharsets.UTF_8);
        final String noncePrependedToPostData = nonce + encodedPayload;
        final MessageDigest md = MessageDigest.getInstance(SHA_256);

        md.update(noncePrependedToPostData.getBytes(StandardCharsets.UTF_8));

        final byte[] messageHash = md.digest();
        final byte[] base64DecodedSecret = Base64.getDecoder().decode(privateKey);
        final SecretKeySpec keyspec = new SecretKeySpec(base64DecodedSecret, HMAC_SHA_512);

        Mac mac = Mac.getInstance(HMAC_SHA_512);
        mac.init(keyspec);

        mac.reset();
        mac.update(pathInBytes);
        mac.update(messageHash);

        return Base64.getEncoder().encodeToString(mac.doFinal());
    }


    public String postingData(Map<String, String> data) {
        final StringBuilder postData = new StringBuilder();
        for (final Map.Entry<String, String> param : data.entrySet()) {
            if (!postData.isEmpty()) {
                postData.append("&");
            }
            postData.append(param.getKey());
            postData.append("=");
            postData.append(URLEncoder.encode(param.getValue(), StandardCharsets.UTF_8));
        }

        return postData.toString();
    }
}
