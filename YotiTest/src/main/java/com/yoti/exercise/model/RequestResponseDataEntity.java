package com.yoti.exercise.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class RequestResponseDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String request;
    private String response;
    private Long startProcessingTime;
    private Long endProcessingTime;

    public RequestResponseDataEntity() {
    }

    public RequestResponseDataEntity(String request, String response, Long startProcessingTime, Long endProcessingTime) {
        this.request = request;
        this.response = response;
        this.startProcessingTime = startProcessingTime;
        this.endProcessingTime = endProcessingTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Long getStartProcessingTime() {
        return startProcessingTime;
    }

    public void setStartProcessingTime(Long startProcessingTime) {
        this.startProcessingTime = startProcessingTime;
    }

    public Long getEndProcessingTime() {
        return endProcessingTime;
    }

    public void setEndProcessingTime(Long endProcessingTime) {
        this.endProcessingTime = endProcessingTime;
    }
}
