package com.api.appTransitionBanks.dto;

import com.api.appTransitionBanks.enums.ProfileEnum;


public record PersonDTO (

        String id,
        ProfileEnum profile
){}


