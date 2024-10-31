package com.learingspring.demo_spring.PriceSetting.repository;

import com.learingspring.demo_spring.PriceSetting.entity.Price;
import com.learingspring.demo_spring.User.entity.User;
import com.learingspring.demo_spring.enums.ColorType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PriceSettingRepository extends JpaRepository<Price, ColorType> {
     Price findByColorType(String colorType);
     Boolean existsByColorType(String colorType);
}
