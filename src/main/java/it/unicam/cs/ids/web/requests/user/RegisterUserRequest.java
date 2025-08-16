package it.unicam.cs.ids.web.requests.user;

import it.unicam.cs.ids.web.requests.BaseRegisterRequest;
import jakarta.validation.constraints.Min;
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
    @Min(3)
    private String name;
    /** The surname of the user */
    @Min(3)
    private String surname;
}
