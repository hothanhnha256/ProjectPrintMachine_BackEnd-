package com.learingspring.demo_spring.MaterialStorage.controller;

import com.learingspring.demo_spring.MaterialStorage.dto.request.AdjustMaterialRequest;
import com.learingspring.demo_spring.MaterialStorage.dto.request.CreateMaterialRequest;
import com.learingspring.demo_spring.MaterialStorage.dto.request.DeleteMaterialRequest;
import com.learingspring.demo_spring.MaterialStorage.dto.response.MaterialResponse;
import com.learingspring.demo_spring.MaterialStorage.service.MaterialStorageService;
import com.learingspring.demo_spring.exception.ApiResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/materialStorage")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MaterialStorageController {
    MaterialStorageService materialStorageService;

    @GetMapping
    ApiResponse<List<MaterialResponse>> getAllMaterials() {
        return ApiResponse.<List<MaterialResponse>>builder()
                .code(200)
                .result(materialStorageService.getAllMaterials())
                .build();
    }

    @PostMapping("/createMaterialRequest")
    ApiResponse<MaterialResponse> createMaterials(@RequestBody CreateMaterialRequest createMaterialRequest) {
        return ApiResponse.<MaterialResponse>builder()
                .code(200)
                .result(materialStorageService.createMaterial(createMaterialRequest))
                .build();
    }

    @PostMapping("/addjustMaterialRequest")
    ApiResponse<MaterialResponse> addMaterials(AdjustMaterialRequest addMaterialRequest) {
        return ApiResponse.<MaterialResponse>builder()
                .code(200)
                .result(materialStorageService.adjustMaterial(addMaterialRequest))
                .build();
    }
    @DeleteMapping()
    ApiResponse<String> deleteMaterials(DeleteMaterialRequest deleteMaterialRequest) {
        return ApiResponse.<String>builder()
                .code(200)
                .result(materialStorageService.deleteMaterial(deleteMaterialRequest))
                .build();
    }
}
