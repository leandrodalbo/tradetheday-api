package com.open.trade.service;

import com.open.trade.configuration.KrakenProps;
import com.open.trade.data.*;
import com.open.trade.exception.KrakenOrderException;
import com.open.trade.exchangecall.KrakenCall;
import com.open.trade.model.Trade;
import com.open.trade.model.TradeStatus;
import com.open.trade.repository.TradeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Base64;

@Service
public class KrakenOrderService {
    private final Logger logger = LoggerFactory.getLogger(KrakenOrderService.class);

    private final KrakenProps props;
    private final KrakenCall krakenCall;
    private final TradeRepository repository;

    public KrakenOrderService(KrakenProps props, KrakenCall krakenCall, TradeRepository repository) {
        this.props = props;
        this.krakenCall = krakenCall;
        this.repository = repository;
    }

    public Mono<Trade> newTrade(OpenTrade trade) {
        return postOrder(trade.symbol(), trade.volume(), KrakenBuySell.BUY)
                .flatMap(it ->
                        {
                            if (!it.success()) {
                                return Mono.error(new KrakenOrderException());
                            }
                            return repository.save(Trade.of(
                                    trade.symbol(),
                                    trade.volume(),
                                    trade.profitprice(),
                                    trade.stopprice(),
                                    TradeStatus.OPEN
                            ));
                        }
                );
    }

    public Mono<KrakenPostResult> postOrder(String symbol, double volume, KrakenBuySell buySell) {
        KrakenOrderPostBody body = new KrakenOrderPostBody(
                System.currentTimeMillis(),
                props.orderType(),
                buySell.toString(),
                volume,
                symbol
        );

        return krakenCall.postOrder(new KrakenOrderPost(
                props.apiKey(),
                signature(body),
                props.privateUriPath(),
                body
        ));

    }

    private String signature(KrakenOrderPostBody body) {
        byte[] macData = new byte[0];

        try {
            String noncePostData = body.nonce() + body.asParams();
            byte[] decodedSecret = Base64.getDecoder().decode(props.apiSecret());

            Mac mac = Mac.getInstance(props.macAlgorithm());
            SecretKeySpec keySpec = new SecretKeySpec(decodedSecret, props.macAlgorithm());
            mac.init(keySpec);

            byte[] sha256PostData = MessageDigest.getInstance(props.shaAlgorithm()).digest(noncePostData.getBytes());
            byte[] pathAndSha256 = new byte[props.privateUriPath().length() + sha256PostData.length];

            System.arraycopy(props.privateUriPath().getBytes(), 0, pathAndSha256, 0, props.privateUriPath().length());
            System.arraycopy(sha256PostData, 0, pathAndSha256, props.privateUriPath().length(), sha256PostData.length);

            macData = mac.doFinal(pathAndSha256);

        } catch (Exception e) {

            logger.warn("Kraken Signature Failed");
            logger.warn(e.getMessage());
        }
        return Base64.getEncoder().encodeToString(macData);
    }
}
