package com.learingspring.demo_spring.User.dto.request;

import com.learingspring.demo_spring.customAnotation.dob.DobConstraint;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PUBLIC)
public class UserUpdatePasswordRequest {
    @Size(min = 8, max = 30, message = "INVALID_PASS")
    String oldPassword;

    @Size(min = 8, max = 30, message = "INVALID_PASS")
    String password;
}
