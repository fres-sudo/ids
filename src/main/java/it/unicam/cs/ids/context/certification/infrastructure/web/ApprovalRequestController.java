package it.unicam.cs.ids.context.certification.infrastructure.web;

import it.unicam.cs.ids.context.certification.application.services.ApprovalRequestService;
import it.unicam.cs.ids.context.certification.infrastructure.web.dtos.ApprovalRequestDTO;
import it.unicam.cs.ids.shared.application.Approvable;
import it.unicam.cs.ids.shared.application.Messages;
import it.unicam.cs.ids.shared.infrastructure.web.ApprovableOperations;
import it.unicam.cs.ids.shared.infrastructure.web.factories.ApiResponseFactory;
import it.unicam.cs.ids.shared.infrastructure.web.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("certifier/requests")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ApprovalRequestController implements ApprovableOperations<ApprovalRequestDTO<Approvable>> {

    private final ApiResponseFactory responseFactory;
    private final ApprovalRequestService approvalRequestService;

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
