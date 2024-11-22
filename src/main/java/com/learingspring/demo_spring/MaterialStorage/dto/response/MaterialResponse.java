package com.learingspring.demo_spring.MaterialStorage.dto.response;

import com.learingspring.demo_spring.enums.MaterialType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MaterialResponse {

    Long id;

    MaterialType name;

    Long value;

    LocalDate dateUpdate;
}
