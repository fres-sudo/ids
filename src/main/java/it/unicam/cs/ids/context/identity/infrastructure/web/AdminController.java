package it.unicam.cs.ids.context.identity.infrastructure.web;

import it.unicam.cs.ids.context.certification.infrastructure.web.dtos.CertifierRequestDTO;
import it.unicam.cs.ids.context.identity.application.services.AdminService;
import it.unicam.cs.ids.shared.infrastructure.web.ApprovableOperations;
import it.unicam.cs.ids.shared.infrastructure.web.factories.ApiResponseFactory;
import it.unicam.cs.ids.shared.infrastructure.web.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ApiResponse<CertifierRequestDTO>> approve(Long requestId, String comments) {
        ApiResponse<CertifierRequestDTO> response = responseFactory.createSuccessResponse(
                "Request Accepted", //TODO use Messages
                adminService.reject(requestId, comments)
        );
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public ResponseEntity<ApiResponse<CertifierRequestDTO>> reject(Long requestId, String comments) {
        ApiResponse<CertifierRequestDTO> response = responseFactory.createSuccessResponse(
                "Request Rejected", //TODO use Messages
                adminService.reject(requestId, comments)
        );
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public ResponseEntity<Page<CertifierRequestDTO>> findPendingRequests(Integer pageNo, Integer pageSize, String sortBy) {
        return ResponseEntity.ok(adminService.findPendingRequests(pageNo, pageSize, sortBy));
    }
}
