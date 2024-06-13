package com.open.trade.controller;

import com.open.trade.service.FetchNewTrades;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(SearchEntriesController.class)
public class SearchEntriesControllerTest {

    @MockBean
    FetchNewTrades service;

    @Autowired
    MockMvc mvc;


    @Test
    void shouldGetSymbolsWithEngulfingResults() throws Exception {
//        given(service.engulfingCandles(projectSymbols.symbols(), "1h")).willReturn(List.of(new EngulfingDto("BTCUSDT", true)));
//
//
//        MockHttpServletResponse res = mvc.perform(get("/crypto/engulfing/1h")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andReturn().getResponse();
//
//        then(res.getStatus()).isEqualTo(HttpStatus.OK.value());
//        verify(service, times(1))
//                .engulfingCandles(projectSymbols.symbols(), "1h");
    }

}
