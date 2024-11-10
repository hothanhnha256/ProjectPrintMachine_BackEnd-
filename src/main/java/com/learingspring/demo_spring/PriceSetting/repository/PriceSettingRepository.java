package com.learingspring.demo_spring.PriceSetting.repository;

import com.learingspring.demo_spring.PriceSetting.entity.Price;
import com.learingspring.demo_spring.enums.ColorType;
import com.learingspring.demo_spring.enums.PageType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PriceSettingRepository extends JpaRepository<Price, ColorType> {

     Price findPriceByColorTypeAndFaceTypeAndPageType(ColorType colorType, Boolean faceType, PageType pageType);
     Boolean existsByColorTypeAndFaceTypeAndPageType(ColorType colorType, Boolean faceType, PageType pageType);

}
