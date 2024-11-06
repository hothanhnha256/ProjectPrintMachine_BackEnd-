package com.learingspring.demo_spring.Wallet.service;

import com.learingspring.demo_spring.User.entity.User;
import com.learingspring.demo_spring.User.repository.UserRepository;
import com.learingspring.demo_spring.Wallet.Mapper.WalletMapper;
import com.learingspring.demo_spring.Wallet.dto.response.WalletResponse;
import com.learingspring.demo_spring.Wallet.enity.Wallet;
import com.learingspring.demo_spring.Wallet.repository.WalletRepository;
import com.learingspring.demo_spring.exception.AppException;
import com.learingspring.demo_spring.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
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


    public WalletResponse createWallet() {
        log.info("creating wallet");

        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User finduser = userRepository.findByUsername(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_EXISTED));

        if(finduser.getWallet() != null){
            throw new AppException(ErrorCode.WALLET_EXISTED);
        }

        Wallet wallet = Wallet.builder()
                .updatedAt(LocalDate.now())
                .balance(0)
                .build();

        wallet = walletRepository.save(wallet);

        finduser.setWallet(wallet);
        userRepository.save(finduser);

        return walletMapper.toWalletResponse(wallet);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public WalletResponse updateWalletBalance(String walletId, Number amount) {
        log.info("updating wallet balance");

        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_WALLET));

        wallet.setBalance(wallet.getBalance().doubleValue() + amount.doubleValue());
        wallet.setUpdatedAt(LocalDate.now());

        wallet = walletRepository.save(wallet);

        return walletMapper.toWalletResponse(wallet);
    }

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
}
