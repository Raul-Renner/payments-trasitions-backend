package com.api.appTransitionBanks.dto;

import com.api.appTransitionBanks.validation.ValidBalance;
import com.api.appTransitionBanks.validation.ValidNumberAccountSender;

public record TransferDTO (

        BankAccountDTO accountReceiver,

        @ValidNumberAccountSender
        BankAccountDTO accountSender,

        @ValidBalance
        Double valueTransfer
){}
