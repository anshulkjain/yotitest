package com.yoti.exercise.controller;

import com.yoti.exercise.model.RequestResponseDataEntity;
import com.yoti.exercise.services.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    @RequestMapping("/moveHoover/history")
    public List<RequestResponseDataEntity> getRequestsHistory() {
        return historyService.getAllRequestHistory();
    }
}
