package it.unicam.cs.ids.context.identity.infrastructure.web.dtos.requests;

import lombok.Data;
import lombok.EqualsAndHashCode;
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
    //FIXME: -- TESTING PURPOSES ONLY --
    public static final String ADMIN_BY_PASS_PASSWORD =  "admin";
}
