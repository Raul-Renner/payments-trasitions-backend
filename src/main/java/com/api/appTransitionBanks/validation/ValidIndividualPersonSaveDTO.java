package com.api.appTransitionBanks.validation;

import jakarta.validation.Constraint;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Document
@Retention(RUNTIME)
@Target({FIELD, PARAMETER})
@Constraint(validatedBy = ValidIndividualPersonSaveDTOValidator.class)
public @interface ValidIndividualPersonSaveDTO {
}
