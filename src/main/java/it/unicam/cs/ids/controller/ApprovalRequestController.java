package it.unicam.cs.ids.controller;

import it.unicam.cs.ids.services.ApprovalRequestService;
import it.unicam.cs.ids.dto.ApprovalRequestDTO;
import it.unicam.cs.ids.shared.application.Approvable;
import it.unicam.cs.ids.shared.application.Messages;
import it.unicam.cs.ids.shared.application.ApprovableOperations;
import it.unicam.cs.ids.shared.infrastructure.api.factories.ApiResponseFactory;
import it.unicam.cs.ids.shared.infrastructure.api.ApiResponse;
import it.unicam.cs.ids.shared.kernel.enums.PlatformRoles;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Controller for handling approval requests made by companies to certifiers.
 * This controller allows certifiers to approve or reject requests and to retrieve pending requests,
 * like adding a new product, bundle to the catalog, or certifying a product.
 * It is accessible only to users with the {@link PlatformRoles#CERTIFIER} role.
 */
@RestController
@RequestMapping("certifier/requests")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ApprovalRequestController implements ApprovableOperations<ApprovalRequestDTO<Approvable>> {
    /** Service for handling approval requests. */
    private final ApprovalRequestService approvalRequestService;
    /** Response factory for creating API responses. */
    private final ApiResponseFactory responseFactory;

    @PreAuthorize("hasRole('CERTIFIER')")
    @Override
    public ApiResponse<ApprovalRequestDTO<Approvable>> approve(Long requestId, String comments) {
        return responseFactory.createSuccessResponse(
                Messages.Success.CERTIFICATION_REQUEST_APPROVED,
                approvalRequestService.approve(requestId, comments));
    }

    @PreAuthorize("hasRole('CERTIFIER')")
    @Override
    public ApiResponse<ApprovalRequestDTO<Approvable>> reject(Long requestId, String comments) {
        return responseFactory.createSuccessResponse(
                Messages.Success.CERTIFICATION_REQUEST_DENIED,
                approvalRequestService.reject(requestId, comments));
    }

    @PreAuthorize("hasRole('CERTIFIER') || hasRole('ADMIN')")
    @GetMapping()
    public Page<ApprovalRequestDTO<Approvable>> findPendingRequests(
            @PageableDefault() Pageable pageable
    ) {
        return approvalRequestService.findPendingRequests(pageable);
    }
}
