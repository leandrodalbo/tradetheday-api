package my.trade.entries;

import my.trade.dto.EngulfingDto;
import my.trade.call.BinanceCall;
import my.trade.candlePattern.TwoCandleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class BullishEngulfingCandleServiceTest {


    @InjectMocks
    private BullishEngulfingCandleService service;
    @Mock
    private TwoCandleService twoCandleService;
    @Mock
    private BinanceCall binanceCall;

    @Test
    void shouldFindABullishEngulfingCandle() {
        given(binanceCall.fetchCandles(any(), anyString(), anyInt())).willReturn((List.of(List.of(
                        1591258320000L,
                        "9640.7",
                        "9642.4",
                        "9640.6",
                        "9642.0",
                        "206",
                        1591258379999L,
                        "2.13660389",
                        48,
                        "119",
                        "1.23424865",
                        "0"
                ),
                List.of(
                        1591258320000L,
                        "9640.7",
                        "9642.4",
                        "9640.6",
                        "9642.0",
                        "206",
                        1591258379999L,
                        "2.13660389",
                        48,
                        "119",
                        "1.23424865",
                        "0"
                )

        )));
        given(twoCandleService.isBullishEngulfing(any(), any())).willReturn(true);

        List<EngulfingDto> dtos = service.engulfingCandles(Set.of("BTCUSDT"), "1h");

        assertThat(dtos.size()).isEqualTo(1);
        assertThat(dtos.get(0)).isEqualTo(new EngulfingDto("BTCUSDT", true));

    }
}
