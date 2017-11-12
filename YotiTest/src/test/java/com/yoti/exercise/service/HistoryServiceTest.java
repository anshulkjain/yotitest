package com.yoti.exercise.service;

import com.yoti.exercise.model.RequestResponseDataEntity;
import com.yoti.exercise.repository.RequestResponseRepository;
import com.yoti.exercise.services.HistoryService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.mock;

public class HistoryServiceTest {

    private HistoryService historyService;
    private RequestResponseRepository repository;

    @Before
    public void setUp() throws Exception {
        historyService = new HistoryService();
        repository = mock(RequestResponseRepository.class);
        Field f = historyService.getClass().getDeclaredField("repository");
        f.setAccessible(true);
        f.set(historyService, repository);
    }

    @Test
    public void shouldReturnAllTheRequestHistory() {
        List<RequestResponseDataEntity> expected = Arrays.asList(new RequestResponseDataEntity(), new RequestResponseDataEntity());
        Mockito.when(repository.findAll()).thenReturn(expected);
        Assert.assertThat(expected, is(equalTo(historyService.getAllRequestHistory())));
    }
}
