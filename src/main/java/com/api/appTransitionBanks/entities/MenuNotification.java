package com.api.appTransitionBanks.entities;

import com.api.appTransitionBanks.enums.NotificationTypeEnum;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@Document(collection = "notification")
public class MenuNotification  implements Serializable {

    @Id
    private ObjectId _id;

    private Person person;

    private String message;

    private Boolean isNotified;

    private Date created;

    private NotificationTypeEnum notificationType;
}
