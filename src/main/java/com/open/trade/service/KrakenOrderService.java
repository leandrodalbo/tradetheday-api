package com.open.trade.service;

import com.open.trade.configuration.KrakenProps;
import com.open.trade.data.KrakenBuySell;
import com.open.trade.data.KrakenOrderPost;
import com.open.trade.data.KrakenOrderPostBody;
import com.open.trade.exchangecall.KrakenCall;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Base64;

public class KrakenOrderService {
    private final Logger logger = LoggerFactory.getLogger(KrakenOrderService.class);

    private final KrakenProps props;
    private final KrakenCall krakenCall;

    public KrakenOrderService(KrakenProps props, KrakenCall krakenCall) {
        this.props = props;
        this.krakenCall = krakenCall;
    }

    public void postOrder(String symbol, double volume, KrakenBuySell buySell) {
        KrakenOrderPostBody body = new KrakenOrderPostBody(
                System.currentTimeMillis(),
                props.orderType(),
                buySell.toString(),
                volume,
                symbol
        );

        krakenCall.postOrder(new KrakenOrderPost(
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
