package com.learingspring.demo_spring.Wallet.controller;

import com.learingspring.demo_spring.Wallet.dto.request.WalletAddBalanceRequest;
import com.learingspring.demo_spring.Wallet.dto.response.WalletResponse;
import com.learingspring.demo_spring.Wallet.service.WalletService;
import com.learingspring.demo_spring.exception.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallet")
@Slf4j
public class WalletController{
    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
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
}
