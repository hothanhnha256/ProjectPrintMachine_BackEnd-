package com.learingspring.demo_spring.File.repository;

import com.learingspring.demo_spring.File.entity.File;
import com.learingspring.demo_spring.Payment.enity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<File, String> {
    Page<File> findAllByUserId(String userId, Pageable pageable);
}
