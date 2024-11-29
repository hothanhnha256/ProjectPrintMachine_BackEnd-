package com.learingspring.demo_spring.ReportWarranty.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReportWarrantyCreateRequest {
    String idMachine;
    String description;
}
