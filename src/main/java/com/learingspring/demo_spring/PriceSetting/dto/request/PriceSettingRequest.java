package com.learingspring.demo_spring.PriceSetting.dto.request;

import com.learingspring.demo_spring.enums.ColorType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PriceSettingRequest {

    String colorType; //BLACK_WHITE AND COLOR
    Number pricePage;


}
