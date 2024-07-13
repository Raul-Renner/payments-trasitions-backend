package com.api.appTransitionBanks.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Document
@Retention(RUNTIME)
@Target({TYPE_USE, FIELD, PARAMETER})
@Constraint(validatedBy = ValidLegalPersonUpdateDTOValidator.class)
public @interface ValidLegalPersonUpdateDTO {
    String message() default "{user.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
