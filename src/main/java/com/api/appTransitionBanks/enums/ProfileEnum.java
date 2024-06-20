package com.api.appTransitionBanks.enums;

import lombok.Getter;

public enum ProfileEnum {

    FISICO("FISICO"),
    JURIDICO("JURIDICO");


    private final String role;

    private final String description;

    ProfileEnum(String description) {
        this.role = "ROLE_" + this.name();
        this.description = description;
    }

    public String getRole(){ return role; }
}
