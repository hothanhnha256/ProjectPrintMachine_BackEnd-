package com.learingspring.demo_spring.PrintMachine.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AvailablePrintersResponse {
    String id;
    String name;
    int blackWhiteInkStatus;
    int colorInkStatus;
    int a0paperStatus;
    int a1paperStatus;
    int a2paperStatus;
    int a3paperStatus;
    int a4paperStatus;
    int a5paperStatus;
    Integer capacity;
    Integer printWaiting;

    public AvailablePrintersResponse(String id, String name, int blackWhiteInkStatus, int colorInkStatus, int a0paperStatus, int a1paperStatus, int a2paperStatus, int a3paperStatus, int a4paperStatus, int a5paperStatus,Integer capacity, Integer printWaiting) {
        this.id = id;
        this.name = name;
        this.blackWhiteInkStatus = blackWhiteInkStatus;
        this.colorInkStatus = colorInkStatus;
        this.a0paperStatus = a0paperStatus;
        this.a1paperStatus = a1paperStatus;
        this.a2paperStatus = a2paperStatus;
        this.a3paperStatus = a3paperStatus;
        this.a4paperStatus = a4paperStatus;
        this.a5paperStatus = a5paperStatus;
        this.capacity = capacity;
        this.printWaiting = printWaiting;
    }

}
