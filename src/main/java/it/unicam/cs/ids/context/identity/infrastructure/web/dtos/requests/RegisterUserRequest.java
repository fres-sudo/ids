package it.unicam.cs.ids.context.identity.infrastructure.web.dtos.requests;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * Represents a requests to register a new user.
 * This class contains the necessary information for creating a user account.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RegisterUserRequest extends BaseRegisterRequest {
    /** The name of the user */
    @Size(min = 3)
    private String name;
    /** The surname of the user */
    @Size(min = 3)
    private String surname;
}
