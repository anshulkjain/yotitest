package com.yoti.exercise.repository;

import com.yoti.exercise.model.Coordinate;
import com.yoti.exercise.model.RequestParam;
import com.yoti.exercise.model.RequestResponseDataEntity;
import com.yoti.exercise.model.Response;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Arrays;

import static com.yoti.exercise.enums.Direction.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RequestResponseRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RequestResponseRepository repository;

    @Test
    public void shouldSaveRequestResponseDataEntity() {
        RequestParam request = RequestParam.newBuilder()
                .withStartPos(new Coordinate(1, 1))
                .withRoomSize(new Coordinate(5, 5))
                .withPatches(Arrays.asList(new Coordinate(2, 2), new Coordinate(3, 3), new Coordinate(2, 1)))
                .withInstructions(Arrays.asList(E, N, E, N, S, W))
                .build();
        Response response = new Response(new Coordinate(2,2), 0);
        RequestResponseDataEntity entity = new RequestResponseDataEntity(request.toString(), response.toString(), System.currentTimeMillis(), System.currentTimeMillis());
        Long id = (Long) entityManager.persistAndGetId(entity);
        entityManager.flush();
        Assert.assertNotNull(repository.findOne(id));
    }
}
