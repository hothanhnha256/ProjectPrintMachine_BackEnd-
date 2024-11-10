package com.learingspring.demo_spring.Order.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderRequest {
    int quantity;
    String fileId;
    String typePaper;
    String colorType;
}
