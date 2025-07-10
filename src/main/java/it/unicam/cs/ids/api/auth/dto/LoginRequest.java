package it.unicam.cs.ids.api.auth.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Represents a request to log in a generic user.
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode
public class LoginRequest implements Serializable {
    /** The email of the user trying to log in. */
    private String email;
    /** The password of the user trying to log in. */
    private String password;
}
