package com.learingspring.demo_spring.MaterialStorage.dto.request;

import com.learingspring.demo_spring.enums.MaterialType;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdjustMaterialRequest {

    MaterialType name;
    Number value;
}
