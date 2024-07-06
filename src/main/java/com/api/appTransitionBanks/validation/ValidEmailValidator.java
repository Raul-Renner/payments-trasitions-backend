package com.api.appTransitionBanks.validation;

import com.api.appTransitionBanks.fieldQueries.LegalPersonFieldQuery;
import com.api.appTransitionBanks.service.impl.IndividualPersonServiceImpl;
import com.api.appTransitionBanks.service.impl.LegalPersonServiceImpl;
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

    private final LegalPersonServiceImpl legalPersonService;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        var isValid = true;
        var bundle = getBundle("ValidationMessages", getDefault());
        if(individualPersonService.existBy(EMAIL.existBy(List.of(value)))){
            context.buildConstraintViolationWithTemplate(bundle.getString("user.email.exist")).addConstraintViolation();
            isValid = false;
        }else if(legalPersonService.existBy(LegalPersonFieldQuery.EMAIL.existBy(List.of(value)))){
            context.buildConstraintViolationWithTemplate(bundle.getString("user.email.exist")).addConstraintViolation();
            isValid = false;
        }

        return isValid;
    }
}
