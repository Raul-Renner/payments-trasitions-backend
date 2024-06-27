package com.api.appTransitionBanks.validation;

import com.api.appTransitionBanks.service.impl.BankAccountService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Locale;

import static com.api.appTransitionBanks.fieldQueries.BankAccountFieldQuery.ACCOUNT;
import static java.util.Locale.getDefault;
import static java.util.ResourceBundle.getBundle;

@RequiredArgsConstructor
public class ValidNumberAccountValidator implements ConstraintValidator<ValidNumberAccount ,String> {

    private final BankAccountService bankAccountService;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        var isValid = true;
        var bundle = getBundle("ValidationMessages", getDefault());

        if(!bankAccountService.existBy(ACCOUNT.existBy(List.of(value)))){
            context.buildConstraintViolationWithTemplate(bundle.getString("accountNumber.notfound")).addConstraintViolation();
            isValid = false;
        }
        return isValid;
    }
}
