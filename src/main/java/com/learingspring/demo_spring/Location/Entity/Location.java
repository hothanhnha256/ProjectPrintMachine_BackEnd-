package com.learingspring.demo_spring.Location.Entity;

import com.learingspring.demo_spring.Location.Enum.BaseEnum;
import com.learingspring.demo_spring.Location.Enum.BuildingEnum;
import com.learingspring.demo_spring.PrintMachine.entity.PrintMachine;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Enumerated(EnumType.STRING)
    BaseEnum base;
    @Enumerated(EnumType.STRING)
    BuildingEnum building;
    int floor;

//    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<PrintMachine> printMachines;
}
