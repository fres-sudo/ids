package it.unicam.cs.ids.api.auth.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * Represents a request to register a new user.
 * This class contains the necessary information for creating a user account.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RegisterUserRequest extends BaseRegisterRequest {
    /** The name of the user */
    private String name;
    /** The surname of the user */
    private String surname;
}
