package com.learingspring.demo_spring.MaterialStorage.service;


import com.learingspring.demo_spring.MaterialStorage.dto.request.CreateMaterialRequest;
import com.learingspring.demo_spring.MaterialStorage.dto.request.AdjustMaterialRequest;
import com.learingspring.demo_spring.MaterialStorage.dto.request.DeleteMaterialRequest;
import com.learingspring.demo_spring.MaterialStorage.dto.response.MaterialResponse;
import com.learingspring.demo_spring.MaterialStorage.entity.MaterialStorage;
import com.learingspring.demo_spring.MaterialStorage.mapper.MaterialStorageMapper;
import com.learingspring.demo_spring.MaterialStorage.repository.MaterialStorageRepository;
import com.learingspring.demo_spring.exception.AppException;
import com.learingspring.demo_spring.exception.ErrorCode;
import jakarta.validation.constraints.Null;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@PreAuthorize("hasRole('ADMIN')")
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

    public MaterialResponse createMaterial(CreateMaterialRequest createMaterialRequest) {

        log.info(createMaterialRequest.toString());
        if(createMaterialRequest.getName()==null) {
            throw new AppException(ErrorCode.MATERIAL_ERROR);
        }
        if(Boolean.TRUE.equals(materialStorageRepository.existsByName(createMaterialRequest.getName()))){
            throw new AppException(ErrorCode.MATERIAL_ALREADY_EXITS);
        }

        MaterialStorage materialStorage = new MaterialStorage();
        materialStorage.setName(createMaterialRequest.getName());
        materialStorage.setValue(createMaterialRequest.getValue());
        materialStorage.setDateUpdate(LocalDate.now());

        log.info(materialStorage.toString());
        return materialStorageMapper.toMaterialResponse(materialStorageRepository.save(materialStorage));
    }


    public MaterialResponse adjustMaterial(AdjustMaterialRequest updateMaterialRequest) {
        if(Boolean.FALSE.equals(materialStorageRepository.existsByName(updateMaterialRequest.getName()))){
            throw new AppException(ErrorCode.MATERIAL_NOT_EXITS);
        }
        MaterialStorage materialStorage = materialStorageRepository.findByName(updateMaterialRequest.getName());

        materialStorage.setValue(materialStorage.getValue() + updateMaterialRequest.getValue());
        if (materialStorage.getValue().intValue()<0){
            throw new AppException(ErrorCode.MATERIAL_NOT_ENOUGH);
        }
        materialStorage.setDateUpdate(LocalDate.now());
        materialStorageRepository.save(materialStorage);
        return materialStorageMapper.toMaterialResponse(materialStorage);
    }

    public String deleteMaterial(DeleteMaterialRequest deleteMaterialRequest) {
        if(Boolean.FALSE.equals(materialStorageRepository.existsByName(deleteMaterialRequest.getName()))){
            throw new AppException(ErrorCode.MATERIAL_NOT_EXITS);
        }
        materialStorageRepository.delete(materialStorageRepository.findByName(deleteMaterialRequest.getName()));
        return "Success delete material: "+ deleteMaterialRequest.getName();
    }
}
