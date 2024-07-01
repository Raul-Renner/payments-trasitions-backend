package com.api.appTransitionBanks.service.impl;

import com.api.appTransitionBanks.dto.PersonDTO;
import com.api.appTransitionBanks.entities.Person;
import com.api.appTransitionBanks.enums.ProfileEnum;
import com.api.appTransitionBanks.fieldQueries.LegalPersonFieldQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

import static com.api.appTransitionBanks.fieldQueries.IndividualPersonFieldQuery.ACCOUNT;
import static com.api.appTransitionBanks.fieldQueries.IndividualPersonFieldQuery.CPF;
import static com.api.appTransitionBanks.fieldQueries.LegalPersonFieldQuery.CNPJ;
import static java.util.Locale.getDefault;
import static java.util.ResourceBundle.getBundle;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {

    private final IndividualPersonServiceImpl individualPersonService;

    private final LegalPersonServiceImpl legalPersonService;


    @Transactional(rollbackFor = { Exception.class, Throwable.class })

    public void deleteProfile(PersonDTO personDTO){
        var bundle = getBundle("ValidationMessages", getDefault());
        try {
            var exist = personDTO.profile().equals(ProfileEnum.JURIDICA)
            ? legalPersonService.existBy(CNPJ.existBy(List.of(personDTO.id())))
            : individualPersonService.existBy(CPF.existBy(List.of(personDTO.id())));

            if(exist){
                if ((personDTO.profile().equals(ProfileEnum.JURIDICA))) {
                    legalPersonService.deleteProfile(personDTO.id());
                } else {
                    individualPersonService.deleteProfile(personDTO.id());
                }
            }else {
                throw new RuntimeException(bundle.getString("user.notfound"));
            }

        } catch (Exception e) {
            throw new RuntimeException(bundle.getString("user.notfound"));
        }
    }

    @Transactional(rollbackFor = { Exception.class, Throwable.class })
    public Person findBy(String numberAccount) {
        try {
            return individualPersonService.existBy(ACCOUNT.existBy(List.of(numberAccount)))
                    ? individualPersonService.findBy(ACCOUNT.findBy(List.of(numberAccount)))
                    : legalPersonService.findBy(LegalPersonFieldQuery.ACCOUNT.findBy(List.of(numberAccount)));
        } catch (Exception e){
            var bundle = getBundle("ValidationMessages", getDefault());
            throw new RuntimeException(bundle.getString("user.notfound"));
        }
    }



}
