package com.learingspring.demo_spring.Wallet.service;

import com.learingspring.demo_spring.User.entity.User;
import com.learingspring.demo_spring.User.repository.UserRepository;
import com.learingspring.demo_spring.Wallet.dto.request.TopUpRequest;
import com.learingspring.demo_spring.Wallet.repository.WalletRepository;
import com.learingspring.demo_spring.config.VNPAYConfig;
import com.learingspring.demo_spring.exception.AppException;
import com.learingspring.demo_spring.exception.ErrorCode;
import com.learingspring.demo_spring.util.VNPayUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class VNPayService {
    private final VNPAYConfig vnPayConfig;
    private final WalletService walletService;
    private final UserRepository userRepository;

    public static String userId;
    public static double amountWallet;
    public String createPaymentUrl(HttpServletRequest request) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User finduser = userRepository.findByUsername(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_EXISTED));


        userId = finduser.getId();

        long amount = Integer.parseInt(request.getParameter("amount")) * 100L;

        amountWallet = amount / 100.0;

        String bankCode = request.getParameter("bankCode");
        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig();
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParamsMap.put("vnp_BankCode", bankCode);
        }
        vnpParamsMap.put("vnp_IpAddr", VNPayUtil.getIpAddress(request));
        //build query url
        String queryUrl = VNPayUtil.getPaymentURL(vnpParamsMap, true);
        String hashData = VNPayUtil.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VNPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        return vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;
    }
}