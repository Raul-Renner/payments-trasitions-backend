package com.api.appTransitionBanks.validation;

import com.api.appTransitionBanks.fieldQueries.IndividualPersonFieldQuery;
import com.api.appTransitionBanks.service.impl.BankAccountService;
import com.api.appTransitionBanks.service.impl.IndividualPersonServiceImpl;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.api.appTransitionBanks.fieldQueries.BankAccountFieldQuery.ACCOUNT;
import static java.util.Locale.getDefault;
import static java.util.ResourceBundle.getBundle;

@RequiredArgsConstructor
public class ValidNumberAccountSenderValidator implements ConstraintValidator<ValidNumberAccountSender ,String> {

    private final BankAccountService bankAccountService;

    private final IndividualPersonServiceImpl individualPersonService;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        var isValid = true;
        var bundle = getBundle("ValidationMessages", getDefault());

        if(!bankAccountService.existBy(ACCOUNT.existBy(List.of(value)))){
            context.buildConstraintViolationWithTemplate(bundle.getString("accountNumber.notfound")).addConstraintViolation();
            isValid = false;
        } else if(!individualPersonService.existBy(IndividualPersonFieldQuery.ACCOUNT.existBy(List.of(value)))){
            context.buildConstraintViolationWithTemplate(bundle.getString("account.individual.unathorized")).addConstraintViolation();
            isValid = false;
        }
        return isValid;
    }
}
