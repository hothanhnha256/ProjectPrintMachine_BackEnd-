package com.learingspring.demo_spring.Order.repository;

import com.learingspring.demo_spring.Order.enity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order,String> {
    Page<Order> findAllByUserId(String userId, Pageable pageable);
}
