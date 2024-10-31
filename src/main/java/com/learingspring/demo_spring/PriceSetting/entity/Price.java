package com.learingspring.demo_spring.PriceSetting.entity;

import com.learingspring.demo_spring.enums.ColorType;
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


    @Column(unique = true)
    String colorType; //BLACK_WHITE AND COLOR

    Number pricePage;

    LocalDate dateUpdate;
}
