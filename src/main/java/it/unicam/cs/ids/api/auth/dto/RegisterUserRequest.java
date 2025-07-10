package it.unicam.cs.ids.api.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class RegisterUserRequest implements Serializable {
    private String name;
    private String surname;
    private String email;
    private String password;
}
