package com.yoti.exercise.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

public class Response {

    private static final Logger LOG = Logger.getLogger(Response.class);
    private Coordinate endPos;
    private int numberOfCleanedPatches;

    public Response(Coordinate endPos, int numberOfCleanedPatches) {
        this.endPos = endPos;
        this.numberOfCleanedPatches = numberOfCleanedPatches;
    }

    public Coordinate getEndPos() {
        return endPos;
    }

    public int getNumberOfCleanedPatches() {
        return numberOfCleanedPatches;
    }

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            LOG.error("Error occurred while parsing response object into json", e);
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Response)) return false;

        Response response = (Response) o;

        if (getNumberOfCleanedPatches() != response.getNumberOfCleanedPatches()) return false;
        return getEndPos().equals(response.getEndPos());
    }

    @Override
    public int hashCode() {
        int result = getEndPos().hashCode();
        result = 31 * result + getNumberOfCleanedPatches();
        return result;
    }
}
