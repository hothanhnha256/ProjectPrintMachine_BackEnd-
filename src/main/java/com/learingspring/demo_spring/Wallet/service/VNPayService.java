package com.learingspring.demo_spring.Wallet.service;

import com.learingspring.demo_spring.Wallet.dto.request.TopUpRequest;
import com.learingspring.demo_spring.Wallet.dto.response.TopUpResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class VNPayService {

    private static final String vnp_TmnCode = "M3BP8ILX";
    private static final String vnp_HashSecret = "WAXAVACIZ495B9IKG7JVGYMFE4JQAPM7";
    private static final String vnp_Url = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";

    public TopUpResponse createPaymentUrl(TopUpRequest request) {
        log.info("Creating payment url");

        String userId = request.getUserId();
        double amount = request.getAmount();

        Map<String, String> vnp_Params = new TreeMap<>();
        vnp_Params.put("vnp_Version", "2.1.0");
        vnp_Params.put("vnp_Command", "pay");
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount * 100));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", String.valueOf(System.currentTimeMillis()));
        vnp_Params.put("vnp_OrderInfo", "Topup for user: " + userId);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", "http://localhost:8080/wallet/vnpay/return");

        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        for (Map.Entry<String, String> entry : vnp_Params.entrySet()) {
            if (!hashData.isEmpty()) {
                hashData.append('&');
                query.append('&');
            }
            hashData.append(entry.getKey()).append('=').append(entry.getValue());
            query.append(entry.getKey()).append('=').append(entry.getValue());
        }

        String vnp_SecureHash = hmacSHA512(vnp_HashSecret, hashData.toString());
        query.append("&vnp_SecureHash=").append(vnp_SecureHash);

        return TopUpResponse.builder()
                .paymentUrl(vnp_Url + "?" + query.toString())
                .build();
    }

    public boolean verifyPayment(Map<String, String> vnp_Params) {
        String vnp_SecureHash = vnp_Params.remove("vnp_SecureHash");
        StringBuilder hashData = new StringBuilder();
        for (Map.Entry<String, String> entry : vnp_Params.entrySet()) {
            if (!hashData.isEmpty()) {
                hashData.append('&');
            }
            hashData.append(entry.getKey()).append('=').append(entry.getValue());
        }

        String calculatedHash = hmacSHA512(vnp_HashSecret, hashData.toString());
        return calculatedHash.equals(vnp_SecureHash);
    }

    private String hmacSHA512(String key, String data) {
        try {
            byte[] hmacKey = key.getBytes(StandardCharsets.UTF_8);
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(hmacKey);
            byte[] hmacData = md.digest(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hmacData) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("HMAC SHA-512 algorithm not found", e);
        }
    }
}