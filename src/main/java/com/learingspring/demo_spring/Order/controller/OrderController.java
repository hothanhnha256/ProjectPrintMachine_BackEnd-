package com.learingspring.demo_spring.Order.controller;

import com.learingspring.demo_spring.Order.dto.request.OrderRequest;
import com.learingspring.demo_spring.Order.dto.response.OrderResponse;
import com.learingspring.demo_spring.Order.service.OrderService;
import com.learingspring.demo_spring.exception.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public ApiResponse<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest) {
        log.info("Creating order");
        return ApiResponse.<OrderResponse>builder()
                .code(200)
                .result(orderService.createOrder(orderRequest))
                .build();
    }
}
