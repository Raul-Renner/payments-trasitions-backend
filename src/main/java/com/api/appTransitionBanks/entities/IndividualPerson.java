package com.api.appTransitionBanks.entities;


import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Getter
@Setter
@Document(collection = "individual_person")
public class IndividualPerson extends Person implements Serializable {

    private String cpf;
}
