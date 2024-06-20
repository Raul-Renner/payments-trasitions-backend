package com.api.appTransitionBanks.validation;

import com.api.appTransitionBanks.service.impl.IndividualPersonServiceImpl;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.api.appTransitionBanks.fieldQueries.IndividualPersonFieldQuery.EMAIL;
import static java.util.Locale.getDefault;
import static java.util.ResourceBundle.getBundle;

@RequiredArgsConstructor
public class ValidEmailValidator implements ConstraintValidator<ValidEMAIL, String> {

    private final IndividualPersonServiceImpl individualPersonService;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        var isValid = true;
        var bundle = getBundle("ValidationMessages", getDefault());
        if(individualPersonService.existBy(EMAIL.existBy(List.of(value)))){
            context.buildConstraintViolationWithTemplate("user.email.exist").addConstraintViolation();
            isValid = false;
        }

        return isValid;
    }
}
