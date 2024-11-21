package com.learingspring.demo_spring.Payment.controller;

import com.learingspring.demo_spring.Payment.dto.request.PaymentRequest;
import com.learingspring.demo_spring.Payment.dto.response.PaymentResponse;
import com.learingspring.demo_spring.Payment.service.PaymentService;
import com.learingspring.demo_spring.exception.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@Slf4j
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/process")
    public ApiResponse<PaymentResponse> processPayment(@RequestBody PaymentRequest paymentRequest) {
        log.info("Processing payment");
        return ApiResponse.<PaymentResponse>builder()
                .code(200)
                .result(paymentService.processPayment(paymentRequest))
                .build();
    }

    @GetMapping("/get-my-payment")
    public ApiResponse<Page<PaymentResponse>> getAllPaymentByUser(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        return ApiResponse.<Page<PaymentResponse>>builder()
                .code(200)
                .result(paymentService.getAllPaymentByUser(page, size))
                .build();
    }

    @GetMapping("/get-all-payment")
    public ApiResponse<Page<PaymentResponse>> getAllPayment(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        return ApiResponse.<Page<PaymentResponse>>builder()
                .code(200)
                .result(paymentService.getAllPayment(page, size))
                .build();
    }
}
