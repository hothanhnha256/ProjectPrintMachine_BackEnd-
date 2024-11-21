package com.learingspring.demo_spring.File.repository;

import com.learingspring.demo_spring.File.entity.File;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<File, String> {
    Page<File> findAllByUserId(String userId, Pageable pageable);
}
