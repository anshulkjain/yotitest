package com.yoti.exercise.services;

import com.yoti.exercise.enums.Direction;
import com.yoti.exercise.exception.InvalidDirtPatchsException;
import com.yoti.exercise.exception.InvalidStartPositionException;
import com.yoti.exercise.exception.NoDirectionsToMoveHooverException;
import com.yoti.exercise.model.Coordinate;
import com.yoti.exercise.model.RequestParam;
import com.yoti.exercise.model.RequestResponseDataEntity;
import com.yoti.exercise.model.Response;
import com.yoti.exercise.repository.RequestResponseRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

@Service
public class MoveHooverService {

    private static final Logger LOG = Logger.getLogger(MoveHooverService.class);

    @Autowired
    private RequestResponseRepository repository;

    public Response hooverWalk(final RequestParam requestParam) {
        Long startProcessingTime = System.currentTimeMillis();
        validateRequest(requestParam);
        Map<Coordinate, Boolean> cells = getRoomCells(requestParam);
        markDirtPatches(requestParam, cells);
        Response response = moveHoover(requestParam, cells);
        persist(requestParam, response, startProcessingTime);
        return response;
    }

    private Response moveHoover(final RequestParam requestParam, Map<Coordinate, Boolean> cells) {
        Coordinate currentPos = requestParam.getStartPos();
        int numberOfDirtPatchesCleaned = 0;

        for (Direction direction : requestParam.getInstructions()) {
            Coordinate nextPosition = getNextPosition(currentPos, direction);
            if (isInside(nextPosition, requestParam.getRoomSize())) {
                currentPos = nextPosition;
                if (cells.get(nextPosition)) {
                    numberOfDirtPatchesCleaned += 1;
                    cells.put(nextPosition, false);
                }
            }
        }
        return new Response(currentPos, numberOfDirtPatchesCleaned);
    }

    private void persist(RequestParam requestParam, Response response, Long startProcessingTime) {
        repository.save(new RequestResponseDataEntity(requestParam.toString(), response.toString(), startProcessingTime, System.currentTimeMillis()));
    }

    private Map<Coordinate, Boolean> getRoomCells(final RequestParam requestParam) {
        Map<Coordinate, Boolean> cells = new HashMap<>();
        IntStream.rangeClosed(1, requestParam.getRoomSize().getX())
                .forEach(x -> {
                    IntStream.rangeClosed(1, requestParam.getRoomSize().getY())
                            .forEach(y -> cells.put(new Coordinate(x, y), false));
                });
        return cells;
    }

    private void markDirtPatches(RequestParam requestParam, Map<Coordinate, Boolean> cells) {
        requestParam.getPatches()
                .stream()
                .filter(cells::containsKey)
                .forEach(c -> cells.put(c, true));
    }

    private boolean isInside(Coordinate coordinate, Coordinate roomSize) {
        return (coordinate.getX() >= 1 && coordinate.getX() <= roomSize.getX()
                && coordinate.getY() >= 1 && coordinate.getY() <= roomSize.getY());
    }

    private void validateRequest(final RequestParam requestParam) {
        if (requestParam.getInstructions() == null || requestParam.getInstructions().isEmpty()) {
            logError(requestParam);
            throw new NoDirectionsToMoveHooverException("No directions are provided to move hoover");
        }
        if (!isInside(requestParam.getStartPos(), requestParam.getRoomSize())) {
            logError(requestParam);
            throw new InvalidStartPositionException("Hoover start position is not inside the room " + requestParam.getStartPos());
        }
        requestParam.getPatches().stream()
                .filter(coordinate -> !isInside(coordinate, requestParam.getRoomSize()))
                .forEach(coordinate -> {
                    logError(requestParam);
                    throw new InvalidDirtPatchsException("Given Dirt patch is not inside the room for coordinate " + coordinate.toString());
                });
    }

    private void logError(RequestParam requestParam) {
        LOG.error("Request validation failed - " + requestParam.toString());
    }

    private Coordinate getNextPosition(Coordinate currentPos, Direction direction) {
        switch (direction) {
            case N:
                return new Coordinate(currentPos.getX(), currentPos.getY() + 1);
            case S:
                return new Coordinate(currentPos.getX(), currentPos.getY() - 1);
            case E:
                return new Coordinate(currentPos.getX() + 1, currentPos.getY());
            case W:
                return new Coordinate(currentPos.getX() - 1, currentPos.getY());
            default:
                return currentPos;
        }
    }
}
