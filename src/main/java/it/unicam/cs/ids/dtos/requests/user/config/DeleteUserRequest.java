package it.unicam.cs.ids.dtos.requests.user.config;

import it.unicam.cs.ids.dtos.requests.user.BaseUserRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/*+
 * Represents a request to delete a user.
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DeleteUserRequest extends BaseUserRequest {
    /** The email of the user to be deleted. */
    private String email;
    /** The password of the user to be deleted. */
    private String password;
}
