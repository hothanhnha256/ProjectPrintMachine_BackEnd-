package com.learingspring.demo_spring.PriceSetting.repository;

import com.learingspring.demo_spring.PriceSetting.entity.Price;
import com.learingspring.demo_spring.enums.ColorType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PriceSettingRepository extends JpaRepository<Price, ColorType> {

     Price findPriceByColorTypeAndFaceTypeAndPageSize(ColorType colorType, Boolean faceType, Number pageSize);
     Boolean existsByColorTypeAndFaceTypeAndPageSize(ColorType colorType, Boolean faceType, Number pageSize);

}
