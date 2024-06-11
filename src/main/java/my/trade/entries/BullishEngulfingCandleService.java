package my.trade.entries;

import my.trade.data.Candle;
import my.trade.call.BinanceCall;
import my.trade.candlePattern.TwoCandleService;
import my.trade.dto.EngulfingDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class BullishEngulfingCandleService {
    private final int TOTAL_CANDLES = 2;

    private final TwoCandleService twoCandleService;
    private final BinanceCall binanceCall;

    public BullishEngulfingCandleService(TwoCandleService twoCandleService, BinanceCall binanceCall) {
        this.twoCandleService = twoCandleService;
        this.binanceCall = binanceCall;
    }

    public List<EngulfingDto> engulfingCandles(Set<String> symbols, String timeframe) {
        return symbols.stream().map(it -> isLastCandleEngulfing(it, timeframe)).toList();
    }

    private EngulfingDto isLastCandleEngulfing(String symbol, String timeframe) {

        List candles = binanceCall
                .fetchCandles(symbol, timeframe, TOTAL_CANDLES);

        List c0 = (List) candles.get(0);
        List c1 = (List) candles.get(1);

        return new EngulfingDto(
                symbol,
                twoCandleService.isBullishEngulfing(Candle.of(
                                Float.parseFloat((String) c0.get(1)),
                                Float.parseFloat((String) c0.get(2)),
                                Float.parseFloat((String) c0.get(3)),
                                Float.parseFloat((String) c0.get(4))

                        ),
                        Candle.of(
                                Float.parseFloat((String) c1.get(1)),
                                Float.parseFloat((String) c1.get(2)),
                                Float.parseFloat((String) c1.get(3)),
                                Float.parseFloat((String) c1.get(4)))

                ));
    }
}
