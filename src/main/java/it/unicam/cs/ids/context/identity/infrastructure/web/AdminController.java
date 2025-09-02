package it.unicam.cs.ids.context.identity.infrastructure.web;

import it.unicam.cs.ids.context.identity.domain.model.RequestType;
import it.unicam.cs.ids.context.identity.infrastructure.web.dtos.RoleRequestDTO;
import it.unicam.cs.ids.context.identity.application.services.AdminService;
import it.unicam.cs.ids.shared.application.Messages;
import it.unicam.cs.ids.shared.infrastructure.web.ApprovableOperations;
import it.unicam.cs.ids.shared.infrastructure.web.factories.ApiResponseFactory;
import it.unicam.cs.ids.shared.infrastructure.web.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for managing administrative operations on role requests.
 * Handles unified approval/rejection workflow for all role types (Certifier, Animator, etc.)
 * following the Open/Closed principle for extensibility.
 *
 * @see AdminService
 */
@RestController
@RequestMapping("admin/requests")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AdminController implements ApprovableOperations<RoleRequestDTO> {

    private final AdminService adminService;
    private final ApiResponseFactory responseFactory;

    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public ApiResponse<RoleRequestDTO> approve(@Valid Long requestId, @Valid String comments) {
        return responseFactory.createSuccessResponse(
                Messages.Success.CERTIFIER_ACCEPTED,
                adminService.approve(requestId, comments)
        );
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public ApiResponse<RoleRequestDTO> reject(@Valid Long requestId, @Valid String comments) {
        return responseFactory.createSuccessResponse(
                Messages.Success.CERTIFIER_REJECTED,
                adminService.reject(requestId, comments)
        );
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public Page<RoleRequestDTO> findPendingRequests(@Valid Pageable pageable) {
        return adminService.findPendingRequests(null, pageable);
    }
    
    /**
     * Endpoint to get pending requests filtered by request type.
     * 
     * @param requestType the type of role request to filter by
     * @param pageable pagination information
     * @return page of pending role requests of the specified type
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/type/{requestType}")
    public Page<RoleRequestDTO> findPendingRequestsByType(
            @PathVariable RequestType requestType, 
            @Valid Pageable pageable) {
        return adminService.findPendingRequests(requestType, pageable);
    }
}
