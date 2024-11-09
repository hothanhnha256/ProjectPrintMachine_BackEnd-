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

    String inkStatus;
    Integer paperStatus;
    Integer capacity;
    Integer printWaiting;

    LocalDate warrantyDate;
    Boolean status; // BAT/TAT

    LocalDate createDate;
    LocalDate updateDate;
}
