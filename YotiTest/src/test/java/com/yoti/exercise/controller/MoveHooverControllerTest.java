package com.yoti.exercise.controller;

import com.yoti.exercise.exception.InvalidStartPositionException;
import com.yoti.exercise.model.Coordinate;
import com.yoti.exercise.model.RequestParam;
import com.yoti.exercise.model.Response;
import com.yoti.exercise.services.MoveHooverService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static com.yoti.exercise.enums.Direction.*;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(MoveHooverController.class)
public class MoveHooverControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MoveHooverService service;

    @Test
    public void shouldReturnValidResponse() throws Exception {
        RequestParam request = RequestParam.newBuilder()
                .withStartPos(new Coordinate(1, 1))
                .withRoomSize(new Coordinate(5, 5))
                .withPatches(Arrays.asList(new Coordinate(2, 2), new Coordinate(3, 3), new Coordinate(2, 1)))
                .withInstructions(Arrays.asList(E, N, E, N, S, W))
                .build();
        given(service.hooverWalk(any(RequestParam.class))).willReturn(new Response(new Coordinate(3, 3), 5));
        mvc.perform(post("/moveHoover")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("numberOfCleanedPatches", is(5)));
    }

    @Test
    public void shouldReturnExceptionWhenStartPosIsNotInsideTheRoom() throws Exception {
        RequestParam request = RequestParam.newBuilder()
                .withStartPos(new Coordinate(11, 1))
                .withRoomSize(new Coordinate(5, 5))
                .withPatches(Arrays.asList(new Coordinate(2, 2), new Coordinate(3, 3), new Coordinate(2, 1)))
                .withInstructions(Arrays.asList(E, N, E, N, S, W))
                .build();
        given(service.hooverWalk(any(RequestParam.class))).willThrow(InvalidStartPositionException.class);
        mvc.perform(post("/moveHoover")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request.toString()))
                .andExpect(status().isConflict());
    }

}
