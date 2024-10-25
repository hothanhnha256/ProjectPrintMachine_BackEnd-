package com.learingspring.demo_spring.User.entity;

import java.time.LocalDate;
import java.util.Set;

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
    String email;
    String password;
    String firstName;
    String lastName;

    LocalDate birthDate;

    Roles role;
}
