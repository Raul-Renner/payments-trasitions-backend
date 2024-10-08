package com.api.appTransitionBanks.entities;

import com.api.appTransitionBanks.enums.ProfileEnum;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "person")
public abstract class Person implements Serializable {

    @Id
    private ObjectId _id;

    private ProfileEnum profile;

    private UserInformation userInformation;

}
