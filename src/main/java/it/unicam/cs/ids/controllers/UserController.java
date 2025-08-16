package it.unicam.cs.ids.controllers;

import it.unicam.cs.ids.shared.infrastructure.api.factories.ApiResponseFactory;
import it.unicam.cs.ids.shared.infrastructure.api.ApiResponse;
import it.unicam.cs.ids.dto.UserDTO;
import it.unicam.cs.ids.web.requests.user.EditUserRequest;
import it.unicam.cs.ids.services.UserService;
import it.unicam.cs.ids.shared.application.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for managing user-related operations.
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("users")
public class UserController {
    /** Service for managing user operations. */
    private final UserService userService;
    /** Factory for creating API responses. */
    private final ApiResponseFactory responseFactory;

    /**
     * Edits an existing user based on the provided requests.
     * @param request the requests containing user details to be updated
     * @return a response entity containing the updated user information
     */
    @PatchMapping()
    ApiResponse<UserDTO> editUser(@RequestBody EditUserRequest request) {
        UserDTO dto =  userService.editUser(request);
        return responseFactory.createSuccessResponse(
                Messages.Success.USER_UPDATED, dto
        );
    }

    /**
     * Deletes a user based on the provided requests.
     * @return a response entity indicating the success of the deletion
     */
    @DeleteMapping()
    ApiResponse<Void> deleteUser() {
        userService.deleteUser();
        return responseFactory.createSuccessResponse(
                Messages.Success.USER_DELETED, null
        );
    }
}
