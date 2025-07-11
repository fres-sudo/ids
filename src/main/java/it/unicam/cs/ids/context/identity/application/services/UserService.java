package it.unicam.cs.ids.context.identity.application.services;

import it.unicam.cs.ids.context.identity.infrastructure.web.dtos.UserDTO;
import it.unicam.cs.ids.context.identity.infrastructure.web.dtos.requests.EditUserRequest;

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
     *
     * @param request the requests containing the user ID to be deleted
     */
    void deleteUser();
}
