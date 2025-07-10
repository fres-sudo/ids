package it.unicam.cs.ids.api.controllers;

import it.unicam.cs.ids.api.responses.factories.ApiResponseFactory;
import it.unicam.cs.ids.api.responses.models.ApiResponse;
import it.unicam.cs.ids.dtos.requests.user.config.DeleteUserRequest;
import it.unicam.cs.ids.dtos.requests.user.config.EditUserRequest;
import it.unicam.cs.ids.entities.User;
import it.unicam.cs.ids.services.UserService;
import it.unicam.cs.ids.utils.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("users")
public class UserController {
    /** Service for managing user operations. */
    private final UserService userService;
    /** Factory for creating API responses. */
    private final ApiResponseFactory responseFactory;

    /**
     * Edits an existing user based on the provided request.
     * @param request the request containing user details to be updated
     * @return a response entity containing the updated user information
     */
    @PutMapping("edit")
    ResponseEntity<ApiResponse<User>> editUser(@RequestBody EditUserRequest request) {
        User updated =  userService.editUser(request);
        ApiResponse<User> response = responseFactory.createSuccessResponse(
                Messages.Success.USER_UPDATED, updated
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Deletes a user based on the provided request.
     * @param request the request containing user details to be deleted
     * @return a response entity indicating the success of the deletion
     */
    @DeleteMapping("delete")
    ResponseEntity<ApiResponse<Void>> deleteUser(@RequestBody DeleteUserRequest request) {
        userService.deleteUser(request);
        ApiResponse<Void> response = responseFactory.createSuccessResponse(
                Messages.Success.USER_DELETED, null
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
