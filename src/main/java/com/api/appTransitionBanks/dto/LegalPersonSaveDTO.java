package com.api.appTransitionBanks.dto;

import com.api.appTransitionBanks.entities.LegalPerson;
import com.api.appTransitionBanks.entities.UserInformation;
import com.api.appTransitionBanks.enums.ProfileEnum;
import com.api.appTransitionBanks.validation.ValidCNPJ;
import com.api.appTransitionBanks.validation.ValidEMAIL;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;


import java.io.Serializable;

import static com.api.appTransitionBanks.enums.ProfileEnum.JURIDICA;

@Builder
public record LegalPersonSaveDTO(

        ProfileEnum profile,

        @ValidEMAIL
        @NotBlank(message = "Email é obrigatório.")
        @Pattern(regexp = "^[a-zA-Z0-9.!#$%&'*+\\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$", message = "EMAIL INVALIDO")
        String email,

        @NotBlank(message = "nome completo é obrigatório")
        @Pattern(regexp = "^[\\S]+(?: [\\S]+)+$", message = "nome e sobrenome é obrigatorio")
        String fullName,

//        String login,

        @NotBlank(message = "{user.pj.name.corporation.notblank}")
        String corporateReason,

        @ValidCNPJ
        @NotBlank(message = "CNPJ é obrigatório")
        @Pattern(regexp = "(^\\d{2}.\\d{3}.\\d{3}/\\d{4}-\\d{2}$)")
        @Size(min = 18, max = 18, message = "CNPJ deve conter entre 14 numeros")
        String cnpj

) implements Serializable {

    private static final long serialVersionUID = 1L;

    public LegalPerson toEntity(){
        var personPj = new LegalPerson();
        personPj.setCnpj(cnpj);
        personPj.setProfile(profile);
        personPj.setCorporateReason(corporateReason);
        personPj.setProfile(JURIDICA);
        personPj.setUserInformation(
                UserInformation.builder()
                        .email(email)
                        .name(fullName.substring(0, fullName.indexOf(' ')))
                        .lastname(fullName.substring(fullName.indexOf(' ') + 1))
                        .build());
        return personPj;
    }

}
