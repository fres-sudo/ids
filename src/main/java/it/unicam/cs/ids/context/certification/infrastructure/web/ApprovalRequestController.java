package it.unicam.cs.ids.context.certification.infrastructure.web;

import it.unicam.cs.ids.context.certification.application.services.ApprovalRequestService;
import it.unicam.cs.ids.context.certification.infrastructure.web.dtos.ApprovalRequestDTO;
import it.unicam.cs.ids.shared.application.Approvable;
import it.unicam.cs.ids.shared.infrastructure.web.ApprovableOperations;
import it.unicam.cs.ids.shared.infrastructure.web.factories.ApiResponseFactory;
import it.unicam.cs.ids.shared.infrastructure.web.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ApiResponse<ApprovalRequestDTO<Approvable>>> approve(Long requestId, String comments) {
        ApiResponse<ApprovalRequestDTO<Approvable>> response = responseFactory.createSuccessResponse(
                "Request Accepted", //TODO use Messages
                approvalRequestService.approve(requestId, comments));
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('CERTIFIER')")
    @Override
    public ResponseEntity<ApiResponse<ApprovalRequestDTO<Approvable>>> reject(Long requestId, String comments) {
        ApiResponse<ApprovalRequestDTO<Approvable>> response = responseFactory.createSuccessResponse(
                "Request Rejected", //TODO use Messages,
                approvalRequestService.reject(requestId, comments));
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('CERTIFIER') || hasRole('ADMIN')")
    @GetMapping()
    public ResponseEntity<Page<ApprovalRequestDTO<Approvable>>> findPendingRequests(
            Pageable pageable
    ) {
        return new ResponseEntity<>(approvalRequestService.findPendingRequests(pageable), HttpStatus.OK);
    }
}
