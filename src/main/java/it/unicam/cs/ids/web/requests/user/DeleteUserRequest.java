package it.unicam.cs.ids.web.requests.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/*+
 * Represents a requests to delete a user.
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
