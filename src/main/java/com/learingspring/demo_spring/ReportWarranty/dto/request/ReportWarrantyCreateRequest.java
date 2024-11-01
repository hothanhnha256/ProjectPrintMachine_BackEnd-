package com.learingspring.demo_spring.ReportWarranty.dto.request;

import com.learingspring.demo_spring.PrintMachine.entity.PrintMachine;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReportWarrantyCreateRequest {
    String id_machine;
    String description;
    LocalDate createDate;
}
