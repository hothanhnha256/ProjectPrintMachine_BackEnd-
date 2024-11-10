package com.learingspring.demo_spring.HistoryMaterialStorage.entity;

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

    @Enumerated(EnumType.STRING)
    MaterialType name;

    @Min(0)
    Number value;
    LocalDate dateUse;
}
