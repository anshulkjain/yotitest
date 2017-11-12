package com.yoti.exercise.controller;

import com.yoti.exercise.model.RequestParam;
import com.yoti.exercise.model.Response;
import com.yoti.exercise.services.MoveHooverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MoveHooverController {

    @Autowired
    private MoveHooverService moveHooverService;

    @RequestMapping(method = RequestMethod.POST, value = "/moveHoover")
    public Response moveHoover(@RequestBody RequestParam request) {
        return moveHooverService.hooverWalk(request);
    }

}
