package com.learingspring.demo_spring.PrintMachine.entity;

import com.learingspring.demo_spring.Location.Entity.Location;
import com.learingspring.demo_spring.ReportWarranty.entity.ReportWarranty;
import com.learingspring.demo_spring.Wallet.entity.Wallet;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;


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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "locationId") // set name foreign key
    Location address;

    //Status
    int blackWhiteInkStatus;
    int colorInkStatus;
    int a0paperStatus;
    int a1paperStatus;
    int a2paperStatus;
    int a3paperStatus;
    int a4paperStatus;
    int a5paperStatus;
    int capacity;


    @JoinColumn(name = "printWaiting")
    Integer printWaiting;


    Boolean status; // BAT/TAT

    LocalDate createDate;
    LocalDate updateDate;

}
