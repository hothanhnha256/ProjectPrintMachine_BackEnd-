package com.learingspring.demo_spring.Wallet.controller;

import com.learingspring.demo_spring.Wallet.dto.request.TopUpRequest;
import com.learingspring.demo_spring.Wallet.dto.request.VNPayReturnRequest;
import com.learingspring.demo_spring.Wallet.dto.request.WalletAddBalanceRequest;
import com.learingspring.demo_spring.Wallet.dto.response.TopUpResponse;
import com.learingspring.demo_spring.Wallet.dto.response.VNPayResponse;
import com.learingspring.demo_spring.Wallet.dto.response.WalletResponse;
import com.learingspring.demo_spring.Wallet.service.VNPayService;
import com.learingspring.demo_spring.Wallet.service.WalletService;
import com.learingspring.demo_spring.exception.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/wallet")
@Slf4j
public class WalletController{
    private final WalletService walletService;
    private final VNPayService vnPayService;

    public WalletController(WalletService walletService, VNPayService vnPayService) {
        this.walletService = walletService;
        this.vnPayService = vnPayService;
    }

    @PostMapping("/add-balance")
    public ApiResponse<WalletResponse> addWalletBalance(@RequestBody WalletAddBalanceRequest request) {
        log.info("Updating wallet balance");
        return ApiResponse.<WalletResponse>builder()
                .code(200)
                .result(walletService.addBalance(request))
                .build();
    }

    @GetMapping("/get-balance")
    public ApiResponse<WalletResponse> getMyBalance() {
        log.info("Getting wallet balance");
        return ApiResponse.<WalletResponse>builder()
                .code(200)
                .result(walletService.getMyBalance())
                .build();
    }
    @PostMapping("/topup")
    public ApiResponse<TopUpResponse> topUpWallet(@RequestBody TopUpRequest request) {
        return ApiResponse.<TopUpResponse>builder()
                .code(200)
                .result(vnPayService.createPaymentUrl(request))
                .build();
    }

    @PostMapping("/vnpay/return")
    public ApiResponse<VNPayResponse> handleVNPayReturn(@RequestBody VNPayReturnRequest vnpayReturnRequest) {
        boolean isValid = vnPayService.verifyPayment(vnpayReturnRequest.getVnpayResponse());
        if (isValid) {
            String userId = vnpayReturnRequest.getVnpayResponse().get("vnp_OrderInfo").split(": ")[1];
            BigDecimal amount = new BigDecimal(vnpayReturnRequest.getVnpayResponse().get("vnp_Amount")).divide(BigDecimal.valueOf(100));
            walletService.topUpWallet(userId, amount);
            return ApiResponse.<VNPayResponse>builder()
                    .code(200)
                    .result(VNPayResponse.builder().message("Payment success").build())
                    .build();
        } else {
            return ApiResponse.<VNPayResponse>builder()
                    .code(400)
                    .result(VNPayResponse.builder().message("Payment failed").build())
                    .build();
        }
    }
}
