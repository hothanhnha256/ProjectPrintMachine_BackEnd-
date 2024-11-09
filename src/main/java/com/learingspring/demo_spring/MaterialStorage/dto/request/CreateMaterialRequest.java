package com.learingspring.demo_spring.MaterialStorage.dto.request;

import com.learingspring.demo_spring.enums.MaterialType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateMaterialRequest {

    MaterialType name;
    Number value;
}
