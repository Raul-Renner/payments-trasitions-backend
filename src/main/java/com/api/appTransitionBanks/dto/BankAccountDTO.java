package com.api.appTransitionBanks.dto;

import com.api.appTransitionBanks.enums.TypeAccount;
import org.bson.types.ObjectId;

public record BankAccountDTO(


        String numberAccount,

        Double balance,

        TypeAccount typeAccount
){

}
