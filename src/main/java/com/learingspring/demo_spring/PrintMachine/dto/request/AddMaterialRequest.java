package com.learingspring.demo_spring.PrintMachine.dto.request;

import com.learingspring.demo_spring.enums.MaterialType;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddMaterialRequest {
    String printerId;

    MaterialType materialType;

    @Min(1)
    Long amount;
}
