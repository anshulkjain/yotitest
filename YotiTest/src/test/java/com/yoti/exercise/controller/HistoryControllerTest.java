package com.yoti.exercise.controller;

import com.yoti.exercise.model.RequestResponseDataEntity;
import com.yoti.exercise.services.HistoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(HistoryController.class)
public class HistoryControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private HistoryService service;

    @Test
    public void shouldReturnRequestHistory() throws Exception {
        given(service.getAllRequestHistory()).willReturn(asList(new RequestResponseDataEntity(), new RequestResponseDataEntity()));
        mvc.perform(get("/moveHoover/history")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }
}
