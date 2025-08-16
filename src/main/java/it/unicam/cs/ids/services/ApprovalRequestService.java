package it.unicam.cs.ids.services;

import it.unicam.cs.ids.shared.application.Approvable;
import it.unicam.cs.ids.dto.ApprovalRequestDTO;
import it.unicam.cs.ids.web.requests.company.SubmitApprovalRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ApprovalRequestService {
    void submitForApproval(SubmitApprovalRequest request);

    ApprovalRequestDTO<Approvable> approve(Long requestId, String adminComments);

    ApprovalRequestDTO<Approvable> reject(Long requestId, String adminComments);

    Page<ApprovalRequestDTO<Approvable>> findPendingRequests(Pageable pageable);
}
