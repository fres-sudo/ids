package it.unicam.cs.ids.api.auth.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Represents a request to log in a user.
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode
public class LoginRequest implements Serializable {
    private String email;
    private String password;
}
