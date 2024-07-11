package com.api.appTransitionBanks.dto;

import com.api.appTransitionBanks.entities.Person;
import com.api.appTransitionBanks.enums.TypeAccount;
import lombok.Builder;


@Builder
public record AuthenticatedResponseDTO(
         String _id,
         String numberAccount,
         String passwordApp,
         Double balance,
         TypeAccount typeAccount,
         Person person,
         String token
) {
}
