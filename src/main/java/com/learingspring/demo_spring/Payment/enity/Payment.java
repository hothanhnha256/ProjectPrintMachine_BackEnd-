package com.learingspring.demo_spring.Payment.enity;

import com.learingspring.demo_spring.Order.enity.Order;
import com.learingspring.demo_spring.User.entity.User;
import com.learingspring.demo_spring.Wallet.enity.Wallet;
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
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)
    Order order;

    @ManyToOne
    @JoinColumn(name = "wallet_id", referencedColumnName = "id", nullable = false)
    Wallet wallet;

    Number amount;
    LocalDate paymentDate;
    String status;
}
