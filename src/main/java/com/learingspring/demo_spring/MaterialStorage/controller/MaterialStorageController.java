package com.learingspring.demo_spring.MaterialStorage.controller;

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

    ApiResponse<List<MaterialResponse>> getAllMaterials() {
        return ApiResponse.<List<MaterialResponse>>builder()
                .code(200)
                .result(materialStorageService.getAllMaterials())
                .build();
    }
}
