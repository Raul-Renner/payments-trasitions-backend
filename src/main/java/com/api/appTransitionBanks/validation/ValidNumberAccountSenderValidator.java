package com.api.appTransitionBanks.validation;

import com.api.appTransitionBanks.dto.BankAccountDTO;
import com.api.appTransitionBanks.service.impl.BankAccountService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.api.appTransitionBanks.enums.TypeAccount.JURIDICA;
import static com.api.appTransitionBanks.fieldQueries.BankAccountFieldQuery.ACCOUNT;
import static java.util.Locale.getDefault;
import static java.util.ResourceBundle.getBundle;

@RequiredArgsConstructor
public class ValidNumberAccountSenderValidator implements ConstraintValidator<ValidNumberAccountSender , BankAccountDTO> {

    private final BankAccountService bankAccountService;

    @Override
    public boolean isValid(BankAccountDTO value, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        var isValid = true;
        var bundle = getBundle("ValidationMessages", getDefault());

        if(!bankAccountService.existBy(ACCOUNT.existBy(List.of(value.numberAccount())))){
            context.buildConstraintViolationWithTemplate(bundle.getString("accountNumber.notfound")).addConstraintViolation();
            isValid = false;
        } else if(value.typeAccount().equals(JURIDICA)){
            context.buildConstraintViolationWithTemplate(bundle.getString("account.individual.unathorized")).addConstraintViolation();
            isValid = false;
        }
        return isValid;
    }
}
