package com.learingspring.demo_spring.PrintMachine.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AvailablePrintersResponse {
    String id;
    String name;
    String inkStatus;
    Integer paperStatus;
    Integer capacity;
    Integer printWaiting;

    public AvailablePrintersResponse(String id, String name, String inkStatus, Integer paperStatus, Integer capacity, Integer printWaiting) {
        this.id = id;
        this.name = name;
        this.inkStatus = inkStatus;
        this.paperStatus = paperStatus;
        this.capacity = capacity;
        this.printWaiting = printWaiting;
    }

}
