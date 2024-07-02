package com.api.appTransitionBanks.dto;

import com.api.appTransitionBanks.validation.ValidBalance;
import com.api.appTransitionBanks.validation.ValidNumberAccount;

public record DepositeDTO (

        @ValidNumberAccount
        String accountSender,

        @ValidBalance
        Double valueDeposit
) {
}
