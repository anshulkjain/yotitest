package com.yoti.exercise.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoti.exercise.enums.Direction;
import org.apache.log4j.Logger;

import java.util.List;

public class RequestParam {
    private static final Logger LOG = Logger.getLogger(RequestParam.class);
    private Coordinate roomSize;
    private Coordinate startPos;
    private List<Coordinate> patches;
    private List<Direction> instructions;

    //public constructor is needed to deserialize json request in an object. Without this client will see a deserialization error
    public RequestParam() {
    }

    private RequestParam(Builder builder) {
        this.roomSize = builder.roomSize;
        this.startPos = builder.startPos;
        this.patches = builder.patches;
        this.instructions = builder.instructions;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Coordinate getRoomSize() {
        return roomSize;
    }

    public Coordinate getStartPos() {
        return startPos;
    }

    public List<Coordinate> getPatches() {
        return patches;
    }

    public List<Direction> getInstructions() {
        return instructions;
    }

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            LOG.error("Error occurred while parsing request object into json", e);
        }
        return null;
    }

    public static class Builder {
        private Coordinate roomSize;
        private Coordinate startPos;
        private List<Coordinate> patches;
        private List<Direction> instructions;

        public Builder withRoomSize(Coordinate roomSize) {
            this.roomSize = roomSize;
            return this;
        }

        public Builder withStartPos(Coordinate startPos) {
            this.startPos = startPos;
            return this;
        }

        public Builder withPatches(List<Coordinate> patches) {
            this.patches = patches;
            return this;
        }

        public Builder withInstructions(List<Direction> directions) {
            this.instructions = directions;
            return this;
        }

        public RequestParam build() {
            return new RequestParam(this);
        }
    }
}
