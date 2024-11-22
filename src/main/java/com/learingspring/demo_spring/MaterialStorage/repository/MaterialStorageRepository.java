package com.learingspring.demo_spring.MaterialStorage.repository;

import com.learingspring.demo_spring.MaterialStorage.entity.MaterialStorage;
import com.learingspring.demo_spring.enums.MaterialType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialStorageRepository extends JpaRepository<MaterialStorage, Long> {

    Boolean existsByName(MaterialType name);

    MaterialStorage findByName(MaterialType name);
}
