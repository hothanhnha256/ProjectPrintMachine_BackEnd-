package com.learingspring.demo_spring.Auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learingspring.demo_spring.Auth.entity.InvalidateToken;

public interface InvalidateTokenRepository extends JpaRepository<InvalidateToken, String> {}
