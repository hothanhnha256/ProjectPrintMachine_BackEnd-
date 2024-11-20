package com.learingspring.demo_spring.PriceSetting.dto.request;

import com.learingspring.demo_spring.enums.ColorType;
import com.learingspring.demo_spring.enums.PageType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PriceSearchRequest {

    ColorType colorType; //BLACK_WHITE AND COLOR

    PageType pageType;

    Boolean faceType; //0 is 1 face, 1 is 2 faces
}
