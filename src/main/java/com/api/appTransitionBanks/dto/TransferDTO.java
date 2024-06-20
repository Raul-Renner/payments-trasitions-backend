package com.api.appTransitionBanks.dto;

public record TransferDTO (

        String accountReceiver,

        String accountSender,

        Double valueTransfer
){}
