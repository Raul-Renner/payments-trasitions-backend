package com.api.appTransitionBanks.enums;

public enum TypeAccount {
    FISICA("FISICA"),
    JURIDICA("JURIDICA");


    private final String account;

    private final String description;

    TypeAccount(String description) {
        this.description = description;
        this.account = this.name();
    }

    public String getAccount(){ return account; }
}
