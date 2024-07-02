package com.api.appTransitionBanks.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import static java.util.Locale.getDefault;
import static java.util.ResourceBundle.getBundle;

@RequiredArgsConstructor
public class ValidBalanceValidator implements ConstraintValidator<ValidBalance, Double> {

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        boolean isValid = true;
        var bundle = getBundle("ValidationMessages", getDefault());

        if(value <= 0){
            context.buildConstraintViolationWithTemplate(bundle.getString("account.balance.invalid")).addConstraintViolation();
            isValid = false;
        }
        return isValid;
    }
}
