package com.learingspring.demo_spring.Order.service;

import com.learingspring.demo_spring.File.entity.File;
import com.learingspring.demo_spring.File.repository.FileRepository;
import com.learingspring.demo_spring.Order.dto.request.OrderRequest;
import com.learingspring.demo_spring.Order.dto.response.OrderResponse;
import com.learingspring.demo_spring.Order.enity.Order;
import com.learingspring.demo_spring.Order.repository.OrderRepository;
import com.learingspring.demo_spring.PriceSetting.entity.Price;
import com.learingspring.demo_spring.PriceSetting.repository.PriceSettingRepository;
import com.learingspring.demo_spring.User.entity.User;
import com.learingspring.demo_spring.User.repository.UserRepository;
import com.learingspring.demo_spring.exception.AppException;
import com.learingspring.demo_spring.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class OrderService {
    OrderRepository orderRepository;
    PriceSettingRepository priceSettingRepository;
    FileRepository fileRepository;
    UserRepository userRepository;

    @Transactional
    public OrderResponse createOrder(OrderRequest orderRequest){
        log.info("Creating order");

        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_INVALID));

        String fileId = orderRequest.getFileId();
        File file = fileRepository.findById(fileId)
                .orElseThrow(() -> new AppException(ErrorCode.FILE_NOT_FOUND));

        Price price = priceSettingRepository.findByColorType(orderRequest.getColorType());

        Order order = Order.builder()
                .file(file)
                .price(price)
                .user(user)
                .typePaper(orderRequest.getTypePaper())
                .status("PENDING")
                .orderDate(LocalDate.now())
                .build();
        order = orderRepository.save(order);
        return toOrderResponse(order);
    }

    public Page<OrderResponse> getAllOrderByUser(int page, int size){
        Pageable pageable = PageRequest.of(page, size);

        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_INVALID));

        return orderRepository.findAllByUserId(user.getId(), pageable)
                .map(this::toOrderResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Page<OrderResponse> getAllOrder(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return orderRepository.findAll(pageable)
                .map(this::toOrderResponse);
    }

    private OrderResponse toOrderResponse(Order order){
        return OrderResponse.builder()
                .id(order.getId())
                .fileName(order.getFile().getName())
                .price(order.getPrice().getPricePage())
                .colorType(order.getPrice().getColorType())
                .typePaper(order.getTypePaper())
                .status(order.getStatus())
                .orderDate(order.getOrderDate())
                .build();
    }
}
