package com.learingspring.demo_spring.Order.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {
    String id;
    int quantity;
    String fileName;
    Number price;
    String colorType;
    String typePaper;
    String status;
    LocalDate orderDate;
}
