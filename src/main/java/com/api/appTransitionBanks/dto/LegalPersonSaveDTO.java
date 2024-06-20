package com.api.appTransitionBanks.dto;

import com.api.appTransitionBanks.entities.IndividualPerson;
import com.api.appTransitionBanks.entities.LegalPerson;
import com.api.appTransitionBanks.entities.UserInformation;
import com.api.appTransitionBanks.enums.ProfileEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
public record LegalPersonSaveDTO(

        ProfileEnum profile,

        @NotBlank(message = "Email é obrigatório.")
        @Pattern(regexp = "^[a-zA-Z0-9.!#$%&'*+\\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$", message = "EMAIL INVALIDO")
        String email,

        @NotBlank(message = "nome completo é obrigatório")
        @Pattern(regexp = "^[\\S]+(?: [\\S]+)+$", message = "nome e sobrenome é obrigatorio")
        String fullName,

//        String login,

        @NotBlank(message = "CNPJ é obrigatório")
        @Pattern(regexp = "(^\\d{2}.\\d{3}.\\d{3}/\\d{4}-\\d{2}$)")
        @Size(min = 18, max = 18, message = "CNPJ deve conter entre 14 numeros")
        String cnpj
) {

    private static final long serialVersionUID = 1L;

    public LegalPerson toEntity(){
        var personPj = new LegalPerson();
        personPj.setCnpj(cnpj);
        personPj.setProfile(profile);
        personPj.setUserInformation(
                UserInformation.builder()
                        .email(email)
                        .name(fullName.substring(0, fullName.indexOf(' ')))
                        .lastname(fullName.substring(fullName.indexOf(' ') + 1))
                        .build());
        return personPj;
    }

}
