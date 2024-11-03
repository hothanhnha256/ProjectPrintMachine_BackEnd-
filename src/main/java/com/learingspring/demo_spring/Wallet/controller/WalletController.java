package com.learingspring.demo_spring.Wallet.controller;

import com.learingspring.demo_spring.Wallet.dto.request.WalletUpdateRequest;
import com.learingspring.demo_spring.Wallet.dto.response.WalletResponse;
import com.learingspring.demo_spring.Wallet.service.WalletService;
import com.learingspring.demo_spring.exception.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallet")
@Slf4j
public class WalletController{
    @Autowired
    WalletService walletService;


    @PostMapping()
    public ApiResponse<WalletResponse> createWallet() {
        log.info("Creating wallet");
        return ApiResponse.<WalletResponse>builder()
                .code(200)
                .result(walletService.createWallet())
                .build();
    }


    @PutMapping("/update-balance/{id}")
    public ApiResponse<WalletResponse> updateWalletBalance(@RequestBody WalletUpdateRequest request,
                                                           @PathVariable("id") String id) {
        log.info("Updating wallet balance");
        return ApiResponse.<WalletResponse>builder()
                .code(200)
                .result(walletService.updateWalletBalance(id, request.getAmount()))
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
}
