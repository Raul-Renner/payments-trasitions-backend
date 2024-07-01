package com.api.appTransitionBanks.entities;

import com.api.appTransitionBanks.enums.TransitionsTypeEnum;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@Document(collection = "transitions_history")
public class TransitionsHistory implements Serializable {

    @Id
    private ObjectId _id;

    private TransitionsTypeEnum transitionsTypeEnum;

    private Person personReceiver;

    private Person personSender;

    private String description;

    private Double valueTransition;

//    private Boolean status;

    private Date date;

}
