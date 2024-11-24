package com.learingspring.demo_spring.Wallet.controller;

import com.learingspring.demo_spring.Wallet.dto.request.WalletAddBalanceRequest;
import com.learingspring.demo_spring.Wallet.dto.response.TopUpResponse;
import com.learingspring.demo_spring.Wallet.dto.response.WalletResponse;
import com.learingspring.demo_spring.Wallet.service.VNPayService;
import com.learingspring.demo_spring.Wallet.service.WalletService;
import com.learingspring.demo_spring.exception.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/get-balance")
    public ApiResponse<WalletResponse> getMyBalance() {
        log.info("Getting wallet balance");
        return ApiResponse.<WalletResponse>builder()
                .code(200)
                .result(walletService.getMyBalance())
                .build();
    }

    @GetMapping("/vn-pay")
    public ApiResponse<TopUpResponse> topUp(HttpServletRequest request) {
        log.info("Top up wallet");
        return ApiResponse.<TopUpResponse>builder()
                .code(200)
                .result(new TopUpResponse(vnPayService.createPaymentUrl(request)))
                .build();
    }

    @GetMapping("/vn-pay-call-back")
    public ApiResponse<TopUpResponse> payCallbackHandler(HttpServletRequest request) {
        log.info("Handling payment callback");
        String status = request.getParameter("vnp_ResponseCode");
        if (status.equals("00")) {
            walletService.topUpWallet(VNPayService.userId, VNPayService.amountWallet);
            return ApiResponse.<TopUpResponse>builder()
                    .code(200)
                    .result(new TopUpResponse("00"))
                    .build();
        } else {
            return ApiResponse.<TopUpResponse>builder()
                    .code(400)
                    .result(new TopUpResponse("Failed"))
                    .build();
        }
    }
}
