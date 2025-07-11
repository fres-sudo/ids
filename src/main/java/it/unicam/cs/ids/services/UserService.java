package it.unicam.cs.ids.services;

import it.unicam.cs.ids.dtos.UserDTO;
import it.unicam.cs.ids.dtos.requests.user.config.DeleteUserRequest;
import it.unicam.cs.ids.dtos.requests.user.config.EditUserRequest;
import it.unicam.cs.ids.entities.User;

/**
 * Service interface for managing user-related operations.
 */
public interface UserService {
    /**
     * Modifies an existing user based on the provided request.
     *
     * @param request the request containing the user modification details
     * @return the (same) modified user
     */
    UserDTO editUser(EditUserRequest request);

    /**
     * Deletes a user based on the provided request.
     *
     * @param request the request containing the user ID to be deleted
     */
    void deleteUser();
}
