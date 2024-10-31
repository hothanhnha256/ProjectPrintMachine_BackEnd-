package com.learingspring.demo_spring.PriceSetting.mapper;


import com.learingspring.demo_spring.PriceSetting.dto.response.PriceSettingResponse;
import com.learingspring.demo_spring.PriceSetting.entity.Price;
import org.mapstruct.Mapper;
@Mapper(componentModel = "spring")
public interface PriceSettingMapper {
    PriceSettingResponse toPriceResponse(Price price);
}
