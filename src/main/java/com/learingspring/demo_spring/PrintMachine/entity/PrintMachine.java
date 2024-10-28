package com.learingspring.demo_spring.PrintMachine.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;


@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(
//        indexes = {
//                @Index(name="idx_id", columnList = "id")
//        }
)
public class PrintMachine {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String name;
    String manufacturer;
    String model;
    String description;
    String address;

    //Status
    String inkStatus;
    Integer paperStatus;
    Integer capacity;

    //Warranty
    LocalDate warrantyDate;
    Boolean status; // BAT/TAT

    LocalDate createDate;
    LocalDate updateDate;
}
