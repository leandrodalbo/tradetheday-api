package com.open.trade.call.exchange;

import java.util.List;

public record KrakenResponse(
        List<String> error,
        KrakenResponseResult result) {
}
