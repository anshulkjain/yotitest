# Yoti Exercise

###Overview
Solution is written as a Spring Boot rest service. Below are the 2 parts
- Move Hoover Service: It first validates incoming request. If validation passes then it moves the hoover as per request and return hoover's last position and number of dirt patches cleaned else return the validation error to the client.
- History Service: Service method returns all the successful request / response

###Validation Errors

- Invalid Start Position: If it's not inside the room's dimension
- Invalid dirt patch location: If any of dirt patch doesn't fall inside the room's dimension
- No directions to move: If Request doesn't have any instructions
  
###Technologies Used
- Java 8
- Maven
- Spring Boot
- Apache Derby (DB)
- JUnit
- Mockito

###Instructions to run application
- HooverMoveApp is a main class which bootstrap rest services. Run this as a usual java file (Shift + F10).
- Use maven plugin-> mvn spring-boot:run
- NOTE: 
    - Services get started at port 3000. Kindly make sure this port is not being used already. If yes, change the server port number in 'application.properties' file
    - Restart will clean the db and re-initialise it.  

###Tests
- Move Hoover Service (Request Method: POST)
    - URI: http://localhost:3000/moveHoover
    - Request 1) {"roomSize":{"x":5,"y":5},"startPos":{"x":11,"y":1},"patches":[{"x":2,"y":2},{"x":3,"y":3},{"x":2,"y":1}],"instructions":["E","N","E","N","S","W"]}
    - Response 1) Hoover start position is not inside the room Coordinate{x=11, y=1}
    - Request 2) {"roomSize":{"x":5,"y":5},"startPos":{"x":1,"y":1},"patches":[{"x":22,"y":2},{"x":3,"y":3},{"x":2,"y":1}],"instructions":["E","N","E","N","S","W"]}
    - Response 2) Given Dirt patch is not inside the room for coordinate Coordinate{x=22, y=2}
    - Request 3) {"roomSize":{"x":5,"y":5},"startPos":{"x":1,"y":1},"patches":[{"x":2,"y":2},{"x":3,"y":3},{"x":2,"y":1}],"instructions":[]}
    - Response 3) No directions are provided to move hoover
    - Request 4) {"roomSize":{"x":5,"y":5},"startPos":{"x":1,"y":1},"patches":[{"x":2,"y":2},{"x":3,"y":3},{"x":2,"y":1}],"instructions":["E","N","E","N","S","W"]}
    - Response 4) {"endPos":{"x":2,"y":2},"numberOfCleanedPatches":3}

- History Service (Request Method: GET)
    - URI: http://localhost:3000/moveHoover/history
    - Returns all the successfully served request-response history like below
        - [{"id":1,"request":"{\"roomSize\":{\"x\":5,\"y\":5},\"startPos\":{\"x\":1,\"y\":1},\"patches\":[{\"x\":2,\"y\":2},{\"x\":3,\"y\":3},{\"x\":2,\"y\":1}],\"instructions\":[\"E\",\"N\",\"E\",\"N\",\"S\",\"W\"]}","response":"{\"endPos\":{\"x\":2,\"y\":2},\"numberOfCleanedPatches\":3}","startProcessingTime":1510524308025,"endProcessingTime":1510524308077}]
