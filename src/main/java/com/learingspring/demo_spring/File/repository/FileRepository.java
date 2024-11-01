package com.learingspring.demo_spring.File.repository;

import com.learingspring.demo_spring.File.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<File, String> {
    List<File> findAllByUserId(String userID);
}
