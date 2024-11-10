package com.learingspring.demo_spring.Payment.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentResponse {
    String id;
    String orderId;
    String walletId;
    Number amount;
    String status;
    LocalDate paymentDate;
}
