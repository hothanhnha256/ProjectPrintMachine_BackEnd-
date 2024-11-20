package com.learingspring.demo_spring.HistoryMaterialStorage.entity;

import com.learingspring.demo_spring.PrintMachine.entity.PrintMachine;
import com.learingspring.demo_spring.enums.MaterialType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
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
public class HistoryMaterialStorage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name="id_machine", referencedColumnName = "id")
    PrintMachine printMachine;

    @Enumerated(EnumType.STRING)
    MaterialType name;

    Number quantity;

    String description;

    LocalDate dateUse;
}
