package com.learingspring.demo_spring.Wallet.service;

import com.learingspring.demo_spring.User.entity.User;
import com.learingspring.demo_spring.User.repository.UserRepository;
import com.learingspring.demo_spring.Wallet.Mapper.WalletMapper;
import com.learingspring.demo_spring.Wallet.dto.response.HistoryBalanceResponse;
import com.learingspring.demo_spring.Wallet.dto.response.WalletResponse;
import com.learingspring.demo_spring.Wallet.entity.HistoryBalance;
import com.learingspring.demo_spring.Wallet.entity.Wallet;
import com.learingspring.demo_spring.Wallet.repository.HistoryBalanceRepository;
import com.learingspring.demo_spring.Wallet.repository.WalletRepository;
import com.learingspring.demo_spring.exception.AppException;
import com.learingspring.demo_spring.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class WalletService {
    WalletRepository walletRepository;
    WalletMapper walletMapper;
    UserRepository userRepository;
    HistoryBalanceRepository historyBalanceRepository;
    
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
        // save history balance
        HistoryBalance historyBalance = HistoryBalance.builder()
                .balance('-' + amount.toString())
                .wallet(wallet)
                .updatedAt(LocalDate.now())
                .build();
        historyBalanceRepository.save(historyBalance);
        walletRepository.save(wallet);
        return wallet;
    }

    public void topUpWallet(String userId, Number amount) {
        log.info("Top-up wallet");
        User user = userRepository.findById(userId).orElseThrow(()
                -> new AppException(ErrorCode.USER_EXISTED));
        Wallet wallet = user.getWallet();
        wallet.setBalance(wallet.getBalance().doubleValue() + amount.doubleValue());
        wallet.setUpdatedAt(LocalDate.now());
        // save history balance
        HistoryBalance historyBalance = HistoryBalance.builder()
                .balance('+' + amount.toString())
                .wallet(wallet)
                .updatedAt(LocalDate.now())
                .build();
        historyBalanceRepository.save(historyBalance);
        walletRepository.save(wallet);
    }

    public Page<HistoryBalanceResponse> getMyHistoryBalance(int page, int size) {
        log.info("getting history balance");

        Pageable pageable = PageRequest.of(page, size);
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User finduser = userRepository.findByUsername(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_EXISTED));

        Wallet wallet = walletRepository.findById(finduser.getWallet().getId())
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_WALLET));

        return historyBalanceRepository.findAllByWalletId(wallet.getId(), pageable)
                .map(this::toHistoryBalanceResponse);
    }

    private HistoryBalanceResponse toHistoryBalanceResponse(HistoryBalance historyBalance) {
        return HistoryBalanceResponse.builder()
                .balance(historyBalance.getBalance())
                .updatedAt(historyBalance.getUpdatedAt())
                .build();
    }
}
