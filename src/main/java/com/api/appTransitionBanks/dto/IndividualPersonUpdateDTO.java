package com.api.appTransitionBanks.dto;

import com.api.appTransitionBanks.entities.IndividualPerson;
import com.api.appTransitionBanks.entities.UserInformation;
import com.api.appTransitionBanks.enums.ProfileEnum;
import com.api.appTransitionBanks.validation.ValidIndividualPersonUpdateDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
@ValidIndividualPersonUpdateDTO
public record IndividualPersonUpdateDTO(

     ProfileEnum profile,

     @NotBlank(message = "{user.cpf.notblank}")
     @Pattern(regexp = "^[a-zA-Z0-9.!#$%&'*+\\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$", message = "{user.email.invalid}")
     String email,

     @NotBlank(message = "{user.fullname.notblank}")
     @Pattern(regexp = "^[\\S]+(?: [\\S]+)+$", message = "{user.username.name.required}")
     String fullName,

     @NotBlank(message = "{user.individual.cpf.notblank}")
     @Pattern(regexp = "(^\\d{3}\\x2E\\d{3}\\x2E\\d{3}\\x2D\\d{2}$)")
     @Size(min = 14, max = 14, message = "{user.limit.number.cpf}")
     String cpf
){
    public IndividualPerson toEntity(){
        var personPf = new IndividualPerson();
        personPf.setCpf(cpf);
        personPf.setUserInformation(
                UserInformation.builder()
                        .email(email)
                        .name(fullName.substring(0, fullName.indexOf(' ')))
                        .lastname(fullName.substring(fullName.indexOf(' ') + 1))
                        .build());
        return personPf;
    }
}