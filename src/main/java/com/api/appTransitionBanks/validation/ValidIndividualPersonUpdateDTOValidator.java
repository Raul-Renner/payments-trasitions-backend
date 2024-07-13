package com.api.appTransitionBanks.validation;

import com.api.appTransitionBanks.dto.IndividualPersonSaveDTO;
import com.api.appTransitionBanks.fieldQueries.IndividualPersonFieldQuery;
import com.api.appTransitionBanks.service.impl.IndividualPersonServiceImpl;
import com.api.appTransitionBanks.service.impl.LegalPersonServiceImpl;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.api.appTransitionBanks.fieldQueries.IndividualPersonFieldQuery.CPF;
import static com.api.appTransitionBanks.fieldQueries.IndividualPersonFieldQuery.EMAIL_CPF;
import static com.api.appTransitionBanks.fieldQueries.LegalPersonFieldQuery.*;
import static java.util.Locale.getDefault;
import static java.util.ResourceBundle.getBundle;

@RequiredArgsConstructor
public class ValidIndividualPersonUpdateDTOValidator implements ConstraintValidator<ValidIndividualPersonSaveDTO, IndividualPersonSaveDTO> {

    private final IndividualPersonServiceImpl individualPersonService;

    private final LegalPersonServiceImpl legalPersonService;

    @Override
    public boolean isValid(IndividualPersonSaveDTO value, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        var bundle = getBundle("ValidationMessages", getDefault());
        var isValid = true;

        if(individualPersonService.existBy(CPF.existBy(List.of(value.cpf())))){
            if(!individualPersonService.existBy(EMAIL_CPF.existBy(List.of(value.email(), value.cpf())))
                    && individualPersonService.existBy(IndividualPersonFieldQuery.EMAIL.existBy(List.of(value.cpf())))
                    && legalPersonService.existBy(EMAIL.existBy(List.of(value.email())))){
                context.buildConstraintViolationWithTemplate(bundle.getString("user.email.exist"));
                isValid = false;
            }
        }else {
            context.buildConstraintViolationWithTemplate(bundle.getString("user.notfound.cpf"));
            isValid = false;
        }

        return isValid;

    }
}
