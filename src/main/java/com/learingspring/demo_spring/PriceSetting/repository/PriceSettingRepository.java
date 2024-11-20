package com.learingspring.demo_spring.PriceSetting.repository;

import com.learingspring.demo_spring.PriceSetting.entity.Price;
import com.learingspring.demo_spring.enums.ColorType;
import com.learingspring.demo_spring.enums.PageType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface PriceSettingRepository extends JpaRepository<Price, Long> {

     Price findPriceByColorTypeAndFaceTypeAndPageType(ColorType colorType, Boolean faceType, PageType pageType);
     Boolean existsByColorTypeAndFaceTypeAndPageType(ColorType colorType, Boolean faceType, PageType pageType);

     @Query(value = "SELECT * FROM price WHERE "
             + "(:colorType = '' OR color_type LIKE %:colorType%) "
             + "AND (:faceType = '' OR face_type = :faceType) "
             + "AND (:pageType = '' OR page_type = :pageType)", nativeQuery = true)
     List<Price> findAllPrice(
             @Param("colorType") String colorType,
             @Param("faceType") String faceType,
             @Param("pageType") String pageType
     );

}
