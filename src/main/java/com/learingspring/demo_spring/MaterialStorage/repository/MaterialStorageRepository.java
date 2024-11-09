package com.learingspring.demo_spring.MaterialStorage.repository;

import com.learingspring.demo_spring.MaterialStorage.entity.MaterialStorage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialStorageRepository extends JpaRepository<MaterialStorage, Long> {
}
