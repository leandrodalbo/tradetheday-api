package com.open.trade.controller;

import com.open.trade.configuration.ProjectSymbols;
import com.open.trade.dto.EngulfingDto;
import com.open.trade.service.SearchEntriesService;
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

@WebMvcTest(SearchEntriesController.class)
public class SearchEntriesControllerTest {

    @MockBean
    SearchEntriesService service;

    @Autowired
    MockMvc mvc;

    @MockBean
    private ProjectSymbols projectSymbols;

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
