package com.learingspring.demo_spring.User.entity;

import java.time.LocalDate;
import com.learingspring.demo_spring.Wallet.entity.Wallet;
import com.learingspring.demo_spring.customAnotation.email.EmailConstraint;
import com.learingspring.demo_spring.enums.Roles;
import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(name="username",unique = true,columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
    String username;

    @Column(name="mssv",unique = true, nullable = false)
    String mssv;

    @Column(unique = true, nullable = false)
    String email;
    String password;
    String firstName;
    String lastName;
    LocalDate birthDate;

    @Column(nullable = false)
    Long capacity = 0L;

    Roles role;
    LocalDate createdAt;
    LocalDate updatedAt;


    @OneToOne
    @JoinColumn(name = "wallet_id", referencedColumnName = "id")
    Wallet wallet;
}
