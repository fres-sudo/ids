package it.unicam.cs.ids.api.responses.models;

import lombok.Data;

@Data
public class FieldError {
    private String field;
    private String message;
}
