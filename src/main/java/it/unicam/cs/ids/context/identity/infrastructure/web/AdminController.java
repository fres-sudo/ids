package it.unicam.cs.ids.context.identity.infrastructure.web;

import it.unicam.cs.ids.context.identity.infrastructure.web.dtos.CertifierRequestDTO;
import it.unicam.cs.ids.context.identity.application.services.AdminService;
import it.unicam.cs.ids.shared.application.Messages;
import it.unicam.cs.ids.shared.infrastructure.web.ApprovableOperations;
import it.unicam.cs.ids.shared.infrastructure.web.factories.ApiResponseFactory;
import it.unicam.cs.ids.shared.infrastructure.web.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for managing administrative operations.
 *
 * @see AdminService
 */
@RestController
@RequestMapping("admin/requests")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AdminController implements ApprovableOperations<CertifierRequestDTO> {

    private final AdminService adminService;
    private final ApiResponseFactory responseFactory;

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public ApiResponse<CertifierRequestDTO> approve(Long requestId, String comments) {
        return responseFactory.createSuccessResponse(
                Messages.Success.CERTIFIER_ACCEPTED,
                adminService.approve(requestId, comments)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public ApiResponse<CertifierRequestDTO> reject(Long requestId, String comments) {
        return responseFactory.createSuccessResponse(
                Messages.Success.CERTIFIER_ACCEPTED,
                adminService.reject(requestId, comments)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public Page<CertifierRequestDTO> findPendingRequests(Pageable pageable) {
        return adminService.findPendingRequests(pageable);
    }
}
