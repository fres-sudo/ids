package it.unicam.cs.ids.context.identity.infrastructure.web;

import it.unicam.cs.ids.shared.infrastructure.web.factories.ApiResponseFactory;
import it.unicam.cs.ids.shared.infrastructure.web.responses.ApiResponse;
import it.unicam.cs.ids.context.identity.infrastructure.web.dtos.UserDTO;
import it.unicam.cs.ids.context.identity.infrastructure.web.dtos.requests.EditUserRequest;
import it.unicam.cs.ids.context.identity.application.services.UserService;
import it.unicam.cs.ids.shared.application.Messages;
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
     * Edits an existing user based on the provided requests.
     * @param request the requests containing user details to be updated
     * @return a response entity containing the updated user information
     */
    @PatchMapping()
    ResponseEntity<ApiResponse<UserDTO>> editUser(@RequestBody EditUserRequest request) {
        UserDTO dto =  userService.editUser(request);
        ApiResponse<UserDTO> response = responseFactory.createSuccessResponse(
                Messages.Success.USER_UPDATED, dto
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Deletes a user based on the provided requests.
     * @return a response entity indicating the success of the deletion
     */
    @DeleteMapping()
    ResponseEntity<ApiResponse<Void>> deleteUser() {
        userService.deleteUser();
        ApiResponse<Void> response = responseFactory.createSuccessResponse(
                Messages.Success.USER_DELETED, null
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
