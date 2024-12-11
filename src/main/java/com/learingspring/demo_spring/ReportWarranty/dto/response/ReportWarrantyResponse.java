package com.learingspring.demo_spring.ReportWarranty.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReportWarrantyResponse {
    String id;
    String idMachine;
    String name;
    String description;
    LocalDate createDate;
}
