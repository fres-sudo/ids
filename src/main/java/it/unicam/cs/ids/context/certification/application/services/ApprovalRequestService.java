package it.unicam.cs.ids.context.certification.application.services;

import it.unicam.cs.ids.shared.application.Approvable;
import it.unicam.cs.ids.context.certification.infrastructure.web.dtos.ApprovalRequestDTO;
import it.unicam.cs.ids.context.certification.infrastructure.web.dtos.requests.SubmitApprovalRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ApprovalRequestService {
    void submitForApproval(SubmitApprovalRequest request);

    ApprovalRequestDTO<Approvable> approve(Long requestId, String adminComments);

    ApprovalRequestDTO<Approvable> reject(Long requestId, String adminComments);

    Page<ApprovalRequestDTO<Approvable>> findPendingRequests(Pageable pageable);
}
