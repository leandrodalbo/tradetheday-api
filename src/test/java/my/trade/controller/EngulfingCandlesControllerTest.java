package my.trade.controller;

import my.trade.dto.EngulfingDto;
import my.trade.entries.BullishEngulfingCandleService;
import my.trade.configuration.ProjectProps;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(EngulfingCandlesController.class)
public class EngulfingCandlesControllerTest {

    @MockBean
    BullishEngulfingCandleService service;

    @Autowired
    MockMvc mvc;

    @MockBean
    private ProjectProps projectProps;

    @Test
    void shouldGetSymbolsWithEngulfingResults() throws Exception {
        given(service.engulfingCandles(projectProps.symbols(), "1h")).willReturn(List.of(new EngulfingDto("BTCUSDT", true)));


        MockHttpServletResponse res = mvc.perform(get("/crypto/engulfing/1h")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        then(res.getStatus()).isEqualTo(HttpStatus.OK.value());
        verify(service, times(1))
                .engulfingCandles(projectProps.symbols(), "1h");
    }

}
