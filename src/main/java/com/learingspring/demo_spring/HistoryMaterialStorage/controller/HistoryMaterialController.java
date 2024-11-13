package com.learingspring.demo_spring.HistoryMaterialStorage.controller;

import com.learingspring.demo_spring.HistoryMaterialStorage.dto.response.HistoryMaterialResponse;
import com.learingspring.demo_spring.HistoryMaterialStorage.service.HistoryMaterialService;
import com.learingspring.demo_spring.PrintMachine.dto.request.PrintmachineCreationRequest;
import com.learingspring.demo_spring.PrintMachine.dto.response.AvailablePrintersResponse;
import com.learingspring.demo_spring.PrintMachine.entity.PrintMachine;
import com.learingspring.demo_spring.PrintMachine.service.PrintmachineService;
import com.learingspring.demo_spring.exception.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/HistoryMaterial")
public class HistoryMaterialController {
    HistoryMaterialService historyMaterialService;
    @GetMapping()
    ApiResponse<Page<HistoryMaterialResponse>> getHistoryMaterial(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ApiResponse.<Page<HistoryMaterialResponse>>builder()
                .code(200)
                .result(historyMaterialService.getHistoryMaterials(page,size))
                .build();
    }

    @GetMapping("/{historyId}")
    ApiResponse<HistoryMaterialResponse> getHistoryMaterialById(@PathVariable Long historyId) {
        return ApiResponse.<HistoryMaterialResponse>builder()
                .code(200)
                .result(historyMaterialService.getHistoryMaterial(historyId))
                .build();
    }

}