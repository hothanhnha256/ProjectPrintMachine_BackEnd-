package com.learingspring.demo_spring.Payment.controller;

import com.learingspring.demo_spring.Payment.dto.request.PaymentRequest;
import com.learingspring.demo_spring.Payment.dto.response.PaymentResponse;
import com.learingspring.demo_spring.Payment.service.PaymentService;
import com.learingspring.demo_spring.exception.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@Slf4j
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping()
    public ApiResponse<PaymentResponse> processPayment(@RequestBody PaymentRequest paymentRequest) {
        log.info("Processing payment");
        return ApiResponse.<PaymentResponse>builder()
                .code(200)
                .result(paymentService.processPayment(paymentRequest))
                .build();
    }
}
