package com.api.appTransitionBanks.dto;

import com.api.appTransitionBanks.entities.Person;
import com.api.appTransitionBanks.enums.TypeAccount;
import lombok.Builder;

@Builder
public record BankAccountDTO(


        String numberAccount,

        Double balance,

        TypeAccount typeAccount,

        Person person
){

}
