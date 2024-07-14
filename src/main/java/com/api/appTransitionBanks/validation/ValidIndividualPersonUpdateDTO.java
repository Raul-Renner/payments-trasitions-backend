package com.api.appTransitionBanks.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Retention(RUNTIME)
@Target({TYPE})
@Constraint(validatedBy = ValidIndividualPersonUpdateDTOValidator.class)
public @interface ValidIndividualPersonUpdateDTO {

    String message() default "{user.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
