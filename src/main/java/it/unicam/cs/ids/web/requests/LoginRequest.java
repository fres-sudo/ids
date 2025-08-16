package it.unicam.cs.ids.web.requests;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Represents a requests to log in a generic user.
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
