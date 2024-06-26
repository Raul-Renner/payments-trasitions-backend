package com.api.appTransitionBanks.enums;


public enum ProfileEnum {

    FISICA("FISICA"),
    JURIDICA("JURIDICA");


    private final String role;

    private final String description;

    ProfileEnum(String description) {
        this.role = "ROLE_" + this.name();
        this.description = description;
    }

    public String getRole(){ return role; }
}
