package com.learingspring.demo_spring.PriceSetting.entity;

import com.learingspring.demo_spring.enums.ColorType;
import com.learingspring.demo_spring.enums.PageType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;


@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Enumerated(EnumType.STRING)
    ColorType colorType; //BLACK_WHITE AND COLOR

    @Enumerated(EnumType.STRING)
    PageType pageType;

    Boolean faceType; //0 is 1 face, 1 is 2 faces

    Number pricePage;

    LocalDate dateUpdate;
}
