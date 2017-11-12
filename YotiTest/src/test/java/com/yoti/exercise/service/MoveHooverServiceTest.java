package com.yoti.exercise.service;

import com.yoti.exercise.enums.Direction;
import com.yoti.exercise.exception.InvalidDirtPatchsException;
import com.yoti.exercise.exception.InvalidStartPositionException;
import com.yoti.exercise.exception.NoDirectionsToMoveHooverException;
import com.yoti.exercise.model.Coordinate;
import com.yoti.exercise.model.RequestParam;
import com.yoti.exercise.model.RequestResponseDataEntity;
import com.yoti.exercise.model.Response;
import com.yoti.exercise.repository.RequestResponseRepository;
import com.yoti.exercise.services.MoveHooverService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import java.lang.reflect.Field;
import java.util.Arrays;

import static com.yoti.exercise.enums.Direction.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;

public class MoveHooverServiceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private MoveHooverService moveHooverService;
    private RequestResponseRepository repository;

    @Before
    public void setUp() throws Exception {
        moveHooverService = new MoveHooverService();
        repository = mock(RequestResponseRepository.class);
        Field f = moveHooverService.getClass().getDeclaredField("repository");
        f.setAccessible(true);
        f.set(moveHooverService, repository);
    }

    @Test
    public void shouldThrowExceptionWhenStartPositionIsNotInsideTheRoom() {
        RequestParam request = RequestParam.newBuilder()
                .withStartPos(new Coordinate(-1, -1))
                .withRoomSize(new Coordinate(5, 5))
                .withPatches(Arrays.asList(new Coordinate(2, 2), new Coordinate(3, 3)))
                .withInstructions(Arrays.asList(Direction.values()))
                .build();
        thrown.expect(InvalidStartPositionException.class);
        moveHooverService.hooverWalk(request);
    }

    @Test
    public void shouldThrowExceptionWhenGiveDirtPatchesAreNotInsideTheRoom() {
        RequestParam request = RequestParam.newBuilder()
                .withStartPos(new Coordinate(1, 1))
                .withRoomSize(new Coordinate(5, 5))
                .withPatches(Arrays.asList(new Coordinate(2, 2), new Coordinate(13, 3)))
                .withInstructions(Arrays.asList(Direction.values()))
                .build();
        thrown.expect(InvalidDirtPatchsException.class);
        moveHooverService.hooverWalk(request);
    }

    @Test
    public void shouldThrowExceptionWhenNoDirectionsAreProvided() {
        RequestParam request = RequestParam.newBuilder()
                .withStartPos(new Coordinate(1, 1))
                .withRoomSize(new Coordinate(5, 5))
                .withPatches(Arrays.asList(new Coordinate(2, 2), new Coordinate(13, 3)))
                .build();
        thrown.expect(NoDirectionsToMoveHooverException.class);
        moveHooverService.hooverWalk(request);
    }

    @Test
    public void shouldReturnValidResponse() {
        RequestParam request = RequestParam.newBuilder()
                .withStartPos(new Coordinate(1, 1))
                .withRoomSize(new Coordinate(5, 5))
                .withPatches(Arrays.asList(new Coordinate(2, 2), new Coordinate(3, 3), new Coordinate(2, 1)))
                .withInstructions(Arrays.asList(E, N, E, N, S, W))
                .build();
        Mockito.when(repository.save(any(RequestResponseDataEntity.class))).then((Answer<Void>) invocationOnMock -> null);
        Response response = moveHooverService.hooverWalk(request);
        Assert.assertThat(new Response(new Coordinate(2, 2), 3), is(equalTo(response)));
    }

}
