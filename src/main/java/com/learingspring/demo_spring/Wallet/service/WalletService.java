package com.learingspring.demo_spring.Wallet.service;

import com.learingspring.demo_spring.User.entity.User;
import com.learingspring.demo_spring.User.repository.UserRepository;
import com.learingspring.demo_spring.Wallet.Mapper.WalletMapper;
import com.learingspring.demo_spring.Wallet.dto.request.WalletAddBalanceRequest;
import com.learingspring.demo_spring.Wallet.dto.response.WalletResponse;
import com.learingspring.demo_spring.Wallet.entity.Wallet;
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

    public Wallet createNewWallet(User user,Number balance) {
        log.info("Creating new wallet");
        Wallet wallet = new Wallet();
        wallet.setBalance(balance);
        wallet.setUser(user);
        wallet.setUpdatedAt(LocalDate.now());
        return walletRepository.save(wallet);
    }

    public Wallet payment(Number amount) {
        log.info("Payment");
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User finduser = userRepository.findByUsername(name).orElseThrow(()
                -> new AppException(ErrorCode.USER_EXISTED));

        Wallet wallet = walletRepository.findById(finduser.getWallet().getId())
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_WALLET));

        wallet.setBalance(wallet.getBalance().doubleValue() - amount.doubleValue());
        wallet.setUpdatedAt(LocalDate.now());
        walletRepository.save(wallet);
        return wallet;
    }
}