package it.unicam.cs.ids.context.certification.infrastructure.web;

import it.unicam.cs.ids.context.certification.application.services.ApprovalRequestService;
import it.unicam.cs.ids.context.certification.domain.model.Approvable;
import it.unicam.cs.ids.context.certification.infrastructure.web.dtos.ApprovalRequestDTO;
import it.unicam.cs.ids.context.certification.infrastructure.web.dtos.requests.SubmitApprovalRequest;
import it.unicam.cs.ids.shared.infrastructure.web.factories.ApiResponseFactory;
import it.unicam.cs.ids.shared.infrastructure.web.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("requests")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ApprovalRequestController {
    /**
     * Factory for creating API responses
     */
    private final ApiResponseFactory responseFactory;
    private final ApprovalRequestService approvalRequestService;

    @PreAuthorize("hasRole('CERTIFIER')")
    @PostMapping("/approve/{requestId}")
    public ResponseEntity<ApiResponse<ApprovalRequestDTO<Approvable>>> approve(
            @PathVariable Long requestId,
            @RequestBody Optional<String> adminComments) {
        ApiResponse<ApprovalRequestDTO<Approvable>> response = responseFactory.createSuccessResponse(
                "Request Accepted", //TODO use Messages
                approvalRequestService.approve(requestId, adminComments.orElse("")));
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('CERTIFIER')")
    @PostMapping("/reject/{requestId}")
    public ResponseEntity<ApiResponse<ApprovalRequestDTO<Approvable>>> reject(
            @PathVariable Long requestId,
            @RequestBody Optional<String> adminComments) {
        ApiResponse<ApprovalRequestDTO<Approvable>> response = responseFactory.createSuccessResponse(
                "Request Rejected", //TODO use Messages,
                approvalRequestService.reject(requestId, adminComments.orElse("")));
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('CERTIFIER') || hasRole('ADMIN')")
    @GetMapping()
    public ResponseEntity<Page<ApprovalRequestDTO<Approvable>>> findPendingRequests(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        return new ResponseEntity<>(approvalRequestService.findPendingRequests(pageNo, pageSize, sortBy), HttpStatus.OK);
    }
}
