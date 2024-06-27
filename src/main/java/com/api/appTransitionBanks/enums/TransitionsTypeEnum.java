package com.api.appTransitionBanks.enums;

public enum TransitionsTypeEnum {

    DEPOSIT("DEPOSITO"),
    TRANSFER("TRANSFERENCIA");

    private final String description;

    TransitionsTypeEnum(String description) {
        this.description = description;
    }
}
