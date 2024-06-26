package com.api.appTransitionBanks.entities;


import lombok.*;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Getter
@Setter
@Document(collection = "legal_person")
public class LegalPerson extends Person implements Serializable {


    private String cnpj;

    private String corporateReason;

}
