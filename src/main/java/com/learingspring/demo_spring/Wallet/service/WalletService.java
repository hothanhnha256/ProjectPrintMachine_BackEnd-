package com.learingspring.demo_spring.Wallet.service;

import com.learingspring.demo_spring.User.entity.User;
import com.learingspring.demo_spring.User.repository.UserRepository;
import com.learingspring.demo_spring.Wallet.Mapper.WalletMapper;
import com.learingspring.demo_spring.Wallet.dto.request.WalletAddBalanceRequest;
import com.learingspring.demo_spring.Wallet.dto.response.WalletResponse;
import com.learingspring.demo_spring.Wallet.enity.Wallet;
import com.learingspring.demo_spring.Wallet.repository.WalletRepository;
import com.learingspring.demo_spring.exception.AppException;
import com.learingspring.demo_spring.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class WalletService {
    WalletRepository walletRepository;
    WalletMapper walletMapper;
    UserRepository userRepository;


//    @PostAuthorize("returnObject.username == authentication.name")
    public WalletResponse getMyBalance() {
        log.info("getting wallet balance");

        var context = SecurityContextHolder.getContext();

        String name = context.getAuthentication().getName();
        User finduser = userRepository.findByUsername(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_EXISTED));


        Wallet wallet = walletRepository.findById(finduser.getWallet().getId())
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_WALLET));
        return walletMapper.toWalletResponse(wallet);
    }


    public WalletResponse addBalance(WalletAddBalanceRequest request) {
        log.info("Adding balance to wallet");

        var context = SecurityContextHolder.getContext();

        String name = context.getAuthentication().getName();
        User finduser = userRepository.findByUsername(name).orElseThrow(()
                -> new AppException(ErrorCode.USER_EXISTED));

        Wallet wallet = walletRepository.findById(finduser.getWallet().getId())
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_WALLET));

        wallet.setBalance(wallet.getBalance().doubleValue() + request.getAmount().doubleValue());
        wallet.setUpdatedAt(LocalDate.now());
        walletRepository.save(wallet);
        return walletMapper.toWalletResponse(wallet);
    }

    public void topUpWallet(String userId, Number amount) {
        log.info("Top-up wallet");
        User user = userRepository.findById(userId).orElseThrow(()
                -> new AppException(ErrorCode.USER_EXISTED));
        Wallet wallet = user.getWallet();
        wallet.setBalance(wallet.getBalance().doubleValue() + amount.doubleValue());
        walletRepository.save(wallet);
    }
}
