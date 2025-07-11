package it.unicam.cs.ids.context.identity.infrastructure.web.dtos.requests;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Represents a request to register a new admin.
 * This class contains the necessary information for creating an admin account.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RegisterAdminRequest extends RegisterUserRequest {
    /*
    * The password field for admin bypass, the user that wishes to register as an admin
    * should send the correct by pass password
    */
    private String adminByPassPasswordField;
}
