package com.learingspring.demo_spring.customAnotation.dob;

import java.lang.annotation.*;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

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
@Constraint(validatedBy = {DobValidator.class})
public @interface DobConstraint {

    int min() default 1;

    String message() default "Invalid Date of Birth, user must have {min}+";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
