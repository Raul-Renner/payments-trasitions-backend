package com.api.appTransitionBanks.dto;

import com.api.appTransitionBanks.enums.ProfileEnum;
import com.api.appTransitionBanks.validation.ValidPersonId;


public record PersonDTO (

        @ValidPersonId
        String id,
        ProfileEnum profile,

        BankAccountDTO bankAccountDTO

){}


