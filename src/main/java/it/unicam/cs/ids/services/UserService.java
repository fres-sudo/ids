package it.unicam.cs.ids.services;

import it.unicam.cs.ids.dto.UserDTO;
import it.unicam.cs.ids.web.requests.user.EditUserRequest;

/**
 * Service interface for managing user-related operations.
 */
public interface UserService {
    /**
     * Modifies an existing user based on the provided requests.
     *
     * @param request the requests containing the user modification details
     * @return the (same) modified user
     */
    UserDTO editUser(EditUserRequest request);

    /**
     * Deletes a user based on the provided requests.
     */
    void deleteUser();
}
