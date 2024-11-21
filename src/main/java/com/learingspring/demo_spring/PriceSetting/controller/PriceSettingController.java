package com.learingspring.demo_spring.PriceSetting.controller;

import com.learingspring.demo_spring.PriceSetting.dto.request.PriceSearchRequest;
import com.learingspring.demo_spring.PriceSetting.dto.request.PriceSettingRequest;
import com.learingspring.demo_spring.PriceSetting.dto.response.PriceSettingResponse;
import com.learingspring.demo_spring.PriceSetting.service.PriceSettingService;
import com.learingspring.demo_spring.enums.ColorType;
import com.learingspring.demo_spring.enums.PageType;
import com.learingspring.demo_spring.exception.ApiResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/settingPrice")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PriceSettingController {
    PriceSettingService priceSettingService;

    @GetMapping
    ApiResponse<List<PriceSettingResponse>> getAllPriceSettings(
            @RequestParam(value = "colorType", defaultValue = "") String colorType,
            @RequestParam(value = "faceType", defaultValue = "") String faceType,
            @RequestParam(value = "pageType", defaultValue = "") String pageType
    ) {
        return ApiResponse.<List<PriceSettingResponse>>builder()
                .code(200)
                .result(priceSettingService.getAllPriceSettings(
                        colorType, faceType, pageType
                ))
                .build();
    }


    @PostMapping()
    ApiResponse<PriceSettingResponse> updatePriceSettings(@RequestBody PriceSettingRequest priceSettingRequest){
        return ApiResponse.<PriceSettingResponse>builder()
                .code(200)
                .result(priceSettingService.updatePriceSettings(priceSettingRequest))
                .build();
    }

    @PostMapping("/createPrice")
    ApiResponse<PriceSettingResponse> createPriceSetting(@RequestBody PriceSettingRequest priceSettingRequest){
        return ApiResponse.<PriceSettingResponse>builder()
                .code(200)
                .result(priceSettingService.createPriceSettings(priceSettingRequest))
                .build();
    }

}
