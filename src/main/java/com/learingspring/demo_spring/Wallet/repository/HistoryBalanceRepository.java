package com.learingspring.demo_spring.Wallet.repository;

import com.learingspring.demo_spring.Wallet.entity.HistoryBalance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryBalanceRepository extends JpaRepository<HistoryBalance, String> {
    Page<HistoryBalance> findAllByWalletId(String id, Pageable pageable);
}