package com.api.appTransitionBanks.dto;

import com.api.appTransitionBanks.validation.ValidBalance;
import com.api.appTransitionBanks.validation.ValidNumberAccountSender;

public record TransferDTO (

        String accountReceiver,

        @ValidNumberAccountSender
        String accountSender,

        @ValidBalance
        Double valueTransfer
){}
