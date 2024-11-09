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

    ColorType colorType; //BLACK_WHITE AND COLOR

    Number pageSize; // x is Ax

    Boolean faceType; //0 is 1 face, 1 is 2 faces

    Number pricePage;


}
