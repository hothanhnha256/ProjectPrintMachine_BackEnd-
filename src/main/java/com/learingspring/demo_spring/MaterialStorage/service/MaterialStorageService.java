package com.learingspring.demo_spring.MaterialStorage.service;


import com.learingspring.demo_spring.MaterialStorage.dto.response.MaterialResponse;
import com.learingspring.demo_spring.MaterialStorage.mapper.MaterialStorageMapper;
import com.learingspring.demo_spring.MaterialStorage.repository.MaterialStorageRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MaterialStorageService {
    MaterialStorageRepository materialStorageRepository;
    MaterialStorageMapper materialStorageMapper;
    public List<MaterialResponse> getAllMaterials() {
        return materialStorageRepository.findAll().stream().map(materialStorageMapper::toMaterialResponse).toList();
    }
}
