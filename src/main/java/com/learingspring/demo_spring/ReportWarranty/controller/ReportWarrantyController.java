package com.learingspring.demo_spring.ReportWarranty.controller;

import com.learingspring.demo_spring.ReportWarranty.dto.request.ReportWarrantyCreateRequest;
import com.learingspring.demo_spring.ReportWarranty.dto.response.ReportWarrantyResponse;
import com.learingspring.demo_spring.ReportWarranty.service.ReportWarrantyService;
import com.learingspring.demo_spring.exception.ApiResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequestMapping("/reportWarranty")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReportWarrantyController {
    ReportWarrantyService reportWarrantyService;

    @PostMapping()
    ApiResponse<ReportWarrantyResponse> createReportWarranty (@RequestBody ReportWarrantyCreateRequest reportWarrantyCreateRequest) {
        return ApiResponse.<ReportWarrantyResponse>builder()
                .code(200)
                .result(reportWarrantyService.createReportWarranty(reportWarrantyCreateRequest))
                .build();
    }

    @GetMapping("/{machineId}")
    public ApiResponse<Page<ReportWarrantyResponse>> getReportWarrantyByMachineId(
            @PathVariable String machineId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.<Page<ReportWarrantyResponse>>builder()
                .code(200)
                .result(reportWarrantyService.getReportWarrantyByMachineId(machineId, page, size))
                .build();
    }

    @GetMapping
    public ApiResponse<Page<ReportWarrantyResponse>> getAllReportWarranty(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.<Page<ReportWarrantyResponse>>builder()
                .code(200)
                .result(reportWarrantyService.getAllReportWarranty(page, size))
                .build();
    }
}
