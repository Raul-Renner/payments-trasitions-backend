package com.api.appTransitionBanks.validation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.io.Serializable;

@Getter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
public final class MessageValidation implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String fieldName;

    private final String message;

    public MessageValidation(ObjectError error) {
        if(error instanceof FieldError)
            this.fieldName = ((FieldError) error).getField();
        else
            this.fieldName = error.getObjectName();
        this.message = error.getDefaultMessage();
    }

}