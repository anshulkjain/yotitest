package com.yoti.exercise.controller.advice;

import com.yoti.exercise.exception.InvalidDirtPatchsException;
import com.yoti.exercise.exception.InvalidStartPositionException;
import com.yoti.exercise.exception.NoDirectionsToMoveHooverException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {InvalidDirtPatchsException.class, InvalidStartPositionException.class, NoDirectionsToMoveHooverException.class})
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(),
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
}
