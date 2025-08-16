package it.unicam.cs.ids.controllers;

import it.unicam.cs.ids.dto.CertifierRequestDTO;
import it.unicam.cs.ids.models.User;
import it.unicam.cs.ids.services.AdminService;
import it.unicam.cs.ids.shared.application.Messages;
import it.unicam.cs.ids.shared.application.ApprovableOperations;
import it.unicam.cs.ids.shared.infrastructure.api.factories.ApiResponseFactory;
import it.unicam.cs.ids.shared.infrastructure.api.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    /** Service for handling administrative operations related to certifier requests. */
    private final AdminService adminService;
    /** Factory for creating API responses. */
    private final ApiResponseFactory responseFactory;

    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public ApiResponse<CertifierRequestDTO> approve(Long requestId, String comments) {
        return responseFactory.createSuccessResponse(
                Messages.Success.CERTIFIER_ACCEPTED,
                adminService.approve(requestId, comments)
        );
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public ApiResponse<CertifierRequestDTO> reject(Long requestId, String comments) {
        return responseFactory.createSuccessResponse(
                Messages.Success.CERTIFIER_REJECTED,
                adminService.reject(requestId, comments)
        );
    }


    //FIXME : adding @PreAuthorize dosen't work, it seems that thr method sees the user as [BUYER],
    // not as ADMIN, even if the user is logged in as ADMIN, now all c\alls to this method are allowed

    @Override
    public Page<CertifierRequestDTO> findPendingRequests(Pageable pageable) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getAuthorities());
        return adminService.findPendingRequests(pageable);
    }
}
