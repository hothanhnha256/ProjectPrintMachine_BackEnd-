package com.learingspring.demo_spring.Wallet.entity;

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
public class HistoryBalance {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="id")
    String id;

    @ManyToOne
    @JoinColumn(name = "wallet_id")
    Wallet wallet;

    @Column(name = "balance")
    String balance;
    LocalDate updatedAt;
}