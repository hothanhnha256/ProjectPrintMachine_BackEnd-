package com.learingspring.demo_spring.HistoryMaterialStorage.dto.request;

import com.learingspring.demo_spring.enums.MaterialType;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddMaterialHistoryRequest {

    MaterialType name;

    Number value;

    String description;
}
