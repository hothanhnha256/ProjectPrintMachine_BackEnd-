package com.learingspring.demo_spring.HistoryMaterialStorage.dto.response;

import com.learingspring.demo_spring.PrintMachine.entity.PrintMachine;
import com.learingspring.demo_spring.enums.MaterialType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HistoryMaterialResponse {

    Long id;

    String id_machine;

    MaterialType name;

    Number quantity;

    String description;

    LocalDate dateUse;
}
