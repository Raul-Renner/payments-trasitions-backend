package com.api.appTransitionBanks.entities;

import com.api.appTransitionBanks.enums.TypeAccount;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "bankAccount")
public class BankAccount {

    @Id
    private ObjectId _id;

    private String numberAccount;

    private String passwordApp;

    private Double balance;

    private TypeAccount typeAccount;

    @DBRef
    private Person person;



}
