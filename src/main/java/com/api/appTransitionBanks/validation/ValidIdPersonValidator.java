package com.api.appTransitionBanks.validation;


import com.api.appTransitionBanks.service.impl.IndividualPersonServiceImpl;
import com.api.appTransitionBanks.service.impl.LegalPersonServiceImpl;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.api.appTransitionBanks.fieldQueries.IndividualPersonFieldQuery.INDIVIDUAL_PERSON_ID;
import static com.api.appTransitionBanks.fieldQueries.LegalPersonFieldQuery.LEGAL_PERSON_ID;
import static java.util.Locale.getDefault;
import static java.util.ResourceBundle.getBundle;

@RequiredArgsConstructor
public class ValidIdPersonValidator implements ConstraintValidator<ValidPersonId, String> {

    private final LegalPersonServiceImpl legalPersonService;

    private final IndividualPersonServiceImpl individualPersonService;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        var bundle = getBundle("ValidationMessages", getDefault());
        var isValid = true;
        if(!individualPersonService.existBy(INDIVIDUAL_PERSON_ID.existBy(List.of(value)))){
            context.buildConstraintViolationWithTemplate(bundle.getString("user.notfound.id")).addConstraintViolation();
            isValid = false;
        }else if(!legalPersonService.existBy(LEGAL_PERSON_ID.existBy(List.of(value)))){
            context.buildConstraintViolationWithTemplate(bundle.getString("user.notfound.id")).addConstraintViolation();
            isValid = false;
        }

        return isValid;
    }
}
