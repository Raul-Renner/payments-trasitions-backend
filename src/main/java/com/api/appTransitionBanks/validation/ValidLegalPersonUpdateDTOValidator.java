package com.api.appTransitionBanks.validation;

import com.api.appTransitionBanks.dto.LegalPersonUpdateDTO;
import com.api.appTransitionBanks.fieldQueries.IndividualPersonFieldQuery;
import com.api.appTransitionBanks.service.impl.IndividualPersonServiceImpl;
import com.api.appTransitionBanks.service.impl.LegalPersonServiceImpl;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.api.appTransitionBanks.fieldQueries.LegalPersonFieldQuery.*;
import static java.util.Locale.getDefault;
import static java.util.ResourceBundle.getBundle;

@RequiredArgsConstructor
public class ValidLegalPersonUpdateDTOValidator implements ConstraintValidator<ValidLegalPersonUpdateDTO, LegalPersonUpdateDTO> {

    private final LegalPersonServiceImpl legalPersonService;

    private final IndividualPersonServiceImpl individualPersonService;

    @Override
    public boolean isValid(LegalPersonUpdateDTO value, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        var bundle = getBundle("ValidationMessages", getDefault());
        var isValid = true;

        if(legalPersonService.existBy(CNPJ.existBy(List.of(value.cnpj())))){
            var user = legalPersonService.findBy(CNPJ.findBy(List.of(value.cnpj())));
            if(!user.getUserInformation().getEmail().equals(value.email())
                   && (legalPersonService.existBy(EMAIL.existBy(List.of(value.email())))
                   || individualPersonService.existBy(IndividualPersonFieldQuery.EMAIL.existBy(List.of(value.email()))))){
               context.buildConstraintViolationWithTemplate(bundle.getString("user.email.exist")).addConstraintViolation();
               isValid = false;
           }
       }else {
           context.buildConstraintViolationWithTemplate(bundle.getString("user.notfound.cnpj")).addConstraintViolation();
           isValid = false;
       }

        return isValid;
    }
}
