package it.unicam.cs.ids.context.identity.infrastructure.web;


import it.unicam.cs.ids.context.company.domain.models.Company;
import it.unicam.cs.ids.context.company.infrastructure.web.dtos.CompanyDTO;
import it.unicam.cs.ids.context.company.infrastructure.web.dtos.requests.EditCompanyRequest;
import it.unicam.cs.ids.context.identity.application.services.AdminService;
import it.unicam.cs.ids.context.identity.domain.model.User;
import it.unicam.cs.ids.context.identity.infrastructure.web.dtos.UserDTO;
import it.unicam.cs.ids.context.identity.infrastructure.web.dtos.requests.EditUserRequest;
import it.unicam.cs.ids.shared.application.Messages;
import it.unicam.cs.ids.shared.infrastructure.web.factories.ApiResponseFactory;
import it.unicam.cs.ids.shared.infrastructure.web.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for managing administrative operations.
 * This controller provides endpoints for editing and deleting users and companies.
 * It requires administrative privileges to access these endpoints.
 *
 * @see AdminService
 */
@RestController
@RequestMapping("admin")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AdminController {
    private final AdminService adminService;
    /**
     * Factory for creating API responses
     */
    private final ApiResponseFactory responseFactory;

    /**
     * Edits an existing user.
     *
     * @param id the ID of the user to edit
     * @param request the request containing the details to edit the user
     * @return a response entity containing the updated user
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping("/users/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> editUser(
            @PathVariable Long id,
            @RequestBody EditUserRequest request
    ) {
        UserDTO dto = adminService.editEntity(User.class, id, request);
        ApiResponse<UserDTO> response = responseFactory.createSuccessResponse(
                Messages.Success.USER_UPDATED, dto
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Deletes a user.
     *
     * @param id the ID of the user to delete
     * @return a response entity indicating success or failure
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping("/companies/{id}")
    public ResponseEntity<ApiResponse<CompanyDTO>> editCompany(
            @PathVariable Long id,
            @RequestBody EditCompanyRequest request
    ) {
        CompanyDTO dto = adminService.editEntity(Company.class, id, request);
        ApiResponse<CompanyDTO> response = responseFactory.createSuccessResponse(
                Messages.Success.COMPANY_UPDATED, dto
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Deletes a user by ID.
     *
     * @param id the ID of the user to delete
     * @return a response entity indicating success or failure
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        adminService.deleteEntity(User.class, id);
        ApiResponse<Void> response = responseFactory.createSuccessResponse(
                Messages.Success.USER_DELETED, null
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Deletes a company by ID.
     *
     * @param id the ID of the company to delete
     * @return a response entity indicating success or failure
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/companies/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCompany(@PathVariable Long id) {
        adminService.deleteEntity(Company.class, id);
        ApiResponse<Void> response = responseFactory.createSuccessResponse(
                Messages.Success.COMPANY_DELETED, null
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Handles certification requests by approving or rejecting them.
     *
     * @param requestId the ID of the certification request
     * @param verdict true to approve, false to reject
     * @return a response entity indicating success or failure
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping("/certifier-requests/{requestId}")
    public ResponseEntity<ApiResponse<Void>> treatCertificationRequest(
            @PathVariable Long requestId,
            @RequestParam boolean verdict
    ) {
        adminService.treatCertificationRequest(requestId, verdict);
        ApiResponse<Void> response;
        if (!verdict) {
            response = responseFactory.createSuccessResponse(
                    Messages.Success.CERTIFICATION_REQUEST_DENIED, null
            );
        } else {
            response = responseFactory.createSuccessResponse(
                    Messages.Success.CERTIFICATION_REQUEST_APPROVED, null
            );
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
