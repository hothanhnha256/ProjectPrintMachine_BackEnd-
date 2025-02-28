package com.learingspring.demo_spring.ReportWarranty.repository;

import com.learingspring.demo_spring.ReportWarranty.entity.ReportWarranty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportWarrantyRepository extends JpaRepository<ReportWarranty, String> {
    Page<ReportWarranty> findAllByIdMachine(String idMachine, Pageable pageable); // Updated to match camelCase field
}
