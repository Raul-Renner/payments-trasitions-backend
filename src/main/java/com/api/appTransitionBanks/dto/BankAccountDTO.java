package com.api.appTransitionBanks.dto;

import com.api.appTransitionBanks.enums.TypeAccount;
import lombok.Builder;
import org.bson.types.ObjectId;

@Builder
public record BankAccountDTO(


        String numberAccount,

        Double balance,

        TypeAccount typeAccount
){

}
