package com.learingspring.demo_spring.Payment.service;

import com.learingspring.demo_spring.Order.enity.Order;
import com.learingspring.demo_spring.Order.repository.OrderRepository;
import com.learingspring.demo_spring.Payment.dto.request.PaymentRequest;
import com.learingspring.demo_spring.Payment.dto.response.PaymentResponse;
import com.learingspring.demo_spring.Payment.enity.Payment;
import com.learingspring.demo_spring.Payment.repository.PaymentRepository;
import com.learingspring.demo_spring.User.entity.User;
import com.learingspring.demo_spring.User.repository.UserRepository;
import com.learingspring.demo_spring.Wallet.enity.Wallet;
import com.learingspring.demo_spring.Wallet.repository.WalletRepository;
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
public class PaymentService {
    PaymentRepository paymentRepository;
    WalletRepository walletRepository;
    OrderRepository orderRepository;
    UserRepository userRepository;

    @Transactional
    public PaymentResponse processPayment(PaymentRequest paymentRequest) {
        log.info("Processing payment") ;

        String walletId = paymentRequest.getWalletId();
        String orderId = paymentRequest.getOrderId();

        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new AppException(ErrorCode.WALLET_NOT_FOUND));

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        String status = "SUCCESS";

        // Calculate total amount
        Number totalAmount = order.getPrice().getPricePage().doubleValue();

        if(wallet.getBalance().doubleValue() < totalAmount.doubleValue()){
            status = "FAILED";
            return PaymentResponse.builder()
                    .status(status)
                    .build();
        }

        wallet.setBalance(wallet.getBalance().doubleValue() - totalAmount.doubleValue());
        walletRepository.save(wallet);

        order.setStatus("PAID");
        orderRepository.save(order);



        Payment payment = Payment.builder()
                .amount(totalAmount)
                .paymentDate(LocalDate.now())
                .order(order)
                .wallet(wallet)
                .status(status)
                .build();
        payment = paymentRepository.save(payment);


        return toPaymentResponse(payment);
    }

    public Page<PaymentResponse> getAllPaymentByUser(int page, int size){
        log.info("Get all payment by user");
        Pageable pageable = PageRequest.of(page, size);

        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_INVALID));

        String walletId = user.getWallet().getId();

        return paymentRepository.findAllByWalletId(walletId, pageable)
                .map(this::toPaymentResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Page<PaymentResponse> getAllPayment(int page, int size){
        log.info("Get all payment");
        Pageable pageable = PageRequest.of(page, size);
        return paymentRepository.findAll(pageable)
                .map(this::toPaymentResponse);
    }

    private PaymentResponse toPaymentResponse(Payment payment){
        return PaymentResponse.builder()
                .id(payment.getId())
                .amount(payment.getAmount())
                .paymentDate(payment.getPaymentDate())
                .status(payment.getStatus())
                .orderId(payment.getOrder().getId())
                .walletId(payment.getWallet().getId())
                .build();
    }
}
