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
@Constraint(validatedBy = ValidNumberAccountSenderValidator.class)
public @interface ValidNumberAccountSender {

    String message() default "{accountNumber.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
