package com.yoti.exercise.services;

import com.yoti.exercise.model.RequestResponseDataEntity;
import com.yoti.exercise.repository.RequestResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HistoryService {

    @Autowired
    private RequestResponseRepository repository;

    public List<RequestResponseDataEntity> getAllRequestHistory() {
        List<RequestResponseDataEntity> history = new ArrayList<>();
        repository.findAll().forEach(history::add);
        return history;
    }
}
