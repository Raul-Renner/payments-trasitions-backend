package com.api.appTransitionBanks.validation;

import com.api.appTransitionBanks.dto.IndividualPersonSaveDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import static java.util.Locale.getDefault;
import static java.util.ResourceBundle.getBundle;

@RequiredArgsConstructor
public class ValidIndividualPersonSaveDTOValidator implements ConstraintValidator<ValidIndividualPersonSaveDTO, IndividualPersonSaveDTO> {
    @Override
    public boolean isValid(IndividualPersonSaveDTO value, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        var bundle = getBundle("ValidationMessages", getDefault());
        var isValid = true;



        return false;
    }
}
