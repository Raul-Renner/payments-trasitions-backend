package com.api.appTransitionBanks.service.impl;

import com.api.appTransitionBanks.dto.PersonDTO;
import com.api.appTransitionBanks.enums.ProfileEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;

import static com.api.appTransitionBanks.fieldQueries.IndividualPersonFieldQuery.CPF;
import static com.api.appTransitionBanks.fieldQueries.LegalPersonFieldQuery.CNPJ;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {

    private final IndividualPersonServiceImpl individualPersonService;

    private final LegalPersonServiceImpl legalPersonService;


    public void deleteProfile(PersonDTO personDTO){
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
                throw new RuntimeException("Usuário não encontrado no sistema!");
            }

        } catch (Exception e) {
            throw new RuntimeException("Usuário não encontrado no sistema!");
        }
    }



}
