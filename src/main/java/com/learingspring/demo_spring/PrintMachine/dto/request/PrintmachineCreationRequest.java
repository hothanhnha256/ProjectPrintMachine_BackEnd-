package com.learingspring.demo_spring.PrintMachine.dto.request;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PrintmachineCreationRequest {
    String id;
    String name;
    String manufacturer;
    String model;
    String description;

    //Location
    String base;
    String building;
    int floor;

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

    LocalDate warrantyDate;
    Boolean status; // BAT/TAT

    LocalDate createDate;
    LocalDate updateDate;
}
