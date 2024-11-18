package com.learingspring.demo_spring.Order.enity;

import com.learingspring.demo_spring.File.entity.File;
import com.learingspring.demo_spring.PriceSetting.entity.Price;
import com.learingspring.demo_spring.User.entity.User;
import jakarta.persistence.*;
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
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="id")
    String id;

    @ManyToOne
    @JoinColumn(name="mssv", referencedColumnName = "mssv", nullable = false)
    User user;


    @ManyToOne
    @JoinColumn(name="price_id", referencedColumnName = "id", nullable = false)
    Price price;

    @ManyToOne
    @JoinColumn(name="file_id", referencedColumnName = "id", nullable = false)
    File file;

    String typePaper;
    String status;
    LocalDate orderDate;
}
