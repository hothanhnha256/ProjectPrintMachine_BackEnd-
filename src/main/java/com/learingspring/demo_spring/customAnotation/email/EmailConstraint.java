package com.learingspring.demo_spring.customAnotation.email;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({
    ElementType.METHOD,
    ElementType.FIELD,
    ElementType.ANNOTATION_TYPE,
    ElementType.CONSTRUCTOR,
    ElementType.PARAMETER,
    ElementType.TYPE_USE
})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {EmailValidator.class})
public @interface EmailConstraint {

    int min() default 1;

    String message() default "Invalid Email, your email must be @hcmut.edu.vn";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
