package com.api.appTransitionBanks.dto;

import com.api.appTransitionBanks.enums.TransitionsTypeEnum;
import lombok.Builder;

@Builder
public record TransitionHistoryDTO (
        TransitionsTypeEnum transitionsTypeEnum,

        BankAccountDTO accountReceiver,

        BankAccountDTO accountPersonSender,

        Double valueTransition
){}
