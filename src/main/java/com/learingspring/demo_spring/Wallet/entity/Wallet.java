package com.learingspring.demo_spring.Wallet.entity;

import com.learingspring.demo_spring.User.entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="id")
    String id;


    @OneToOne(mappedBy = "wallet")
    User user;

    @Column(nullable = false)
    Number balance;
    LocalDate updatedAt;

    @OneToMany
    List<HistoryBalance> historyBalance;

}