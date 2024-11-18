package com.learingspring.demo_spring.Payment.repository;

import com.learingspring.demo_spring.Payment.enity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,String> {
    Page<Payment> findAllByWalletId(String walletId, Pageable pageable);
}
