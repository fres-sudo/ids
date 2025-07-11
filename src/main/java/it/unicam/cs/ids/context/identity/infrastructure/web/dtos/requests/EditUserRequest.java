package it.unicam.cs.ids.context.identity.infrastructure.web.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Represents a requests to edit user details.
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EditUserRequest extends BaseUserRequest {
    /** New name of the user (if applicable) */
    private String name;
    /** New surname of the user (if applicable) */
    private String surname;
    /** New email to update to (if applicable) */
    private String email;
    /** New password to update to (if applicable) */
    private String password;
    /** New description of the user */
    private String description;
    /** New phone number */
    private String phoneNumber;
    /** New address */
    private String address;
}
