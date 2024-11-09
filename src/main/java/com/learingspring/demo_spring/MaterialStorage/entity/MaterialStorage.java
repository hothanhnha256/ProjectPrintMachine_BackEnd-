package com.learingspring.demo_spring.MaterialStorage.entity;

import com.learingspring.demo_spring.enums.ColorType;
import com.learingspring.demo_spring.enums.MaterialType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
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
public class MaterialStorage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    MaterialType name;

    @Min(0)
    Number value;
    LocalDate dateUpdate;
}
