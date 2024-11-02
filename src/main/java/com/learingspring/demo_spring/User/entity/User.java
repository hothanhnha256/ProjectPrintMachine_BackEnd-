package com.learingspring.demo_spring.User.entity;

import java.time.LocalDate;
import java.util.Set;

import com.learingspring.demo_spring.Wallet.enity.Wallet;
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
    Number balance;

    @Column(nullable = false)
    long capacity;

    Roles role;

    LocalDate createdAt;
    LocalDate updatedAt;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    Wallet wallet;

    @PrePersist
    public void prePersist() {
        if (this.wallet == null) {
            this.wallet = new Wallet();
            this.wallet.setUser(this);
            this.wallet.setBalance(0); // Initialize wallet balance
            this.wallet.setUpdatedAt(LocalDate.now());
        }
    }
}
