package com.api.appTransitionBanks.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Retention(RUNTIME)
@Target({FIELD, PARAMETER})
@Constraint(validatedBy = ValidCNPJValidator.class)
public @interface ValidCNPJ {

    String message() default "{lesson.id.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
