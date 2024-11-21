package com.learingspring.demo_spring.Order.controller;

import com.learingspring.demo_spring.Order.dto.request.OrderRequest;
import com.learingspring.demo_spring.Order.dto.response.OrderResponse;
import com.learingspring.demo_spring.Order.service.OrderService;
import com.learingspring.demo_spring.exception.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping("/get-my-order")
    public ApiResponse<Page<OrderResponse>> getAllOrderByUser(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        return ApiResponse.<Page<OrderResponse>>builder()
                .code(200)
                .result(orderService.getAllOrderByUser(page, size))
                .build();
    }

    @GetMapping("/get-all-order")
    public ApiResponse<Page<OrderResponse>> getAllOrder(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        return ApiResponse.<Page<OrderResponse>>builder()
                .code(200)
                .result(orderService.getAllOrder(page, size))
                .build();
    }
}
