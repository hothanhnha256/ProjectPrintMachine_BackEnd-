package com.learingspring.demo_spring.User.dto.request;

import java.time.LocalDate;

import com.learingspring.demo_spring.customAnotation.email.EmailConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import com.learingspring.demo_spring.customAnotation.dob.DobConstraint;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @Size(min = 8, max = 20, message = "INVALID_USER")
    String username;


    @Email(message = "")
    @EmailConstraint(message = "INVALID_HCMUT_EMAIL")
    String email;

    @Size(min = 8, max = 30, message = "INVALID_PASS")
    String password;

    String firstName;
    String lastName;

    @DobConstraint(min = 10, message = "USER_DOB_INVALID")
    LocalDate birthDate;

}