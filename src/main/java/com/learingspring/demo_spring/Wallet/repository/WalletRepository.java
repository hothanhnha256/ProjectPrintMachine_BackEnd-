package com.learingspring.demo_spring.Wallet.repository;

import com.learingspring.demo_spring.Wallet.enity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, String> {
}
