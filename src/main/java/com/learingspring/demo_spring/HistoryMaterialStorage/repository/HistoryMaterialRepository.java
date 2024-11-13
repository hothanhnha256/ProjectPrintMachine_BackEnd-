package com.learingspring.demo_spring.HistoryMaterialStorage.repository;

import com.learingspring.demo_spring.HistoryMaterialStorage.entity.HistoryMaterialStorage;
import com.learingspring.demo_spring.MaterialStorage.entity.MaterialStorage;
import com.learingspring.demo_spring.enums.MaterialType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryMaterialRepository extends JpaRepository<HistoryMaterialStorage, Long> {
}
