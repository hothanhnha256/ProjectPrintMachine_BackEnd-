package com.learingspring.demo_spring.Payment.service;

import com.learingspring.demo_spring.Order.enity.Order;
import com.learingspring.demo_spring.Order.repository.OrderRepository;
import com.learingspring.demo_spring.Payment.dto.request.PaymentRequest;
import com.learingspring.demo_spring.Payment.dto.response.PaymentResponse;
import com.learingspring.demo_spring.Payment.enity.Payment;
//import com.learingspring.demo_spring.Payment.mapper.PaymentMapper;
import com.learingspring.demo_spring.Payment.repository.PaymentRepository;
import com.learingspring.demo_spring.Wallet.enity.Wallet;
import com.learingspring.demo_spring.Wallet.repository.WalletRepository;
import com.learingspring.demo_spring.exception.AppException;
import com.learingspring.demo_spring.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class PaymentService {
    PaymentRepository paymentRepository;
    WalletRepository walletRepository;
    OrderRepository orderRepository;

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
        Number totalAmount = order.getPrice().getPricePage().doubleValue() * order.getQuantity();

        if(wallet.getBalance().doubleValue() < totalAmount.doubleValue()){
            status = "FAILED";
        }

        wallet.setBalance(wallet.getBalance().doubleValue() - totalAmount.doubleValue());
        walletRepository.save(wallet);

        order.setStatus("PAID");
        orderRepository.save(order);



        Payment payment = Payment.builder()
                .amount(totalAmount)
                .paymentDate(LocalDate.now())
                .order(order)
                .status(status)
                .build();
        payment = paymentRepository.save(payment);


        return toPaymentResponse(payment);
    }

    private PaymentResponse toPaymentResponse(Payment payment){
        return PaymentResponse.builder()
                .id(payment.getId())
                .amount(payment.getAmount())
                .paymentDate(payment.getPaymentDate())
                .status(payment.getStatus())
                .orderId(payment.getOrder().getId())
                .build();
    }
}
