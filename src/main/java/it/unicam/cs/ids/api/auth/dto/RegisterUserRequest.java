package it.unicam.cs.ids.api.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Represents a request to register a new user.
 * This class contains the necessary information for creating a user account.
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode
public class RegisterUserRequest implements Serializable {
    /** The name of the user */
    private String name;
    /** The surname of the user */
    private String surname;
    /** The email address of the user */
    private String email;
    /** The password for the user account */
    private String password;
}
