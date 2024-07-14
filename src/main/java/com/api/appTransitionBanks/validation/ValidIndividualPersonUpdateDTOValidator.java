package com.api.appTransitionBanks.validation;

import com.api.appTransitionBanks.dto.IndividualPersonUpdateDTO;
import com.api.appTransitionBanks.fieldQueries.IndividualPersonFieldQuery;
import com.api.appTransitionBanks.service.impl.IndividualPersonServiceImpl;
import com.api.appTransitionBanks.service.impl.LegalPersonServiceImpl;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.api.appTransitionBanks.fieldQueries.IndividualPersonFieldQuery.CPF;
import static com.api.appTransitionBanks.fieldQueries.LegalPersonFieldQuery.*;
import static java.util.Locale.getDefault;
import static java.util.ResourceBundle.getBundle;

@RequiredArgsConstructor
public class ValidIndividualPersonUpdateDTOValidator implements ConstraintValidator<ValidIndividualPersonUpdateDTO, IndividualPersonUpdateDTO> {

    private final IndividualPersonServiceImpl individualPersonService;

    private final LegalPersonServiceImpl legalPersonService;

    @Override
    public void initialize(ValidIndividualPersonUpdateDTO constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(IndividualPersonUpdateDTO value, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        var isValid = true;
        var bundle = getBundle("ValidationMessages", getDefault());
        var user = individualPersonService.findBy(CPF.findBy(List.of(value.cpf())));
        if(individualPersonService.existBy(CPF.existBy(List.of(value.cpf())))){
            if(!user.getUserInformation().getEmail().equals(value.email()) &&
                    (individualPersonService.existBy(IndividualPersonFieldQuery.EMAIL.existBy(List.of(value.email())))
                    || legalPersonService.existBy(EMAIL.existBy(List.of(value.email()))))){
                    context.buildConstraintViolationWithTemplate(bundle.getString("user.email.exist")).addConstraintViolation();
                    isValid = false;
            }
        }else {
            context.buildConstraintViolationWithTemplate(bundle.getString("user.notfound.cpf")).addConstraintViolation();
            isValid = false;
        }

        return isValid;

    }
}
