package com.open.trade.call.exchange;

import java.util.List;

public record KrakenResponseResult(List<List<String>> ohlc) {
}
