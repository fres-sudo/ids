package it.unicam.cs.ids.services;

import it.unicam.cs.ids.shared.application.Approvable;
import it.unicam.cs.ids.dto.ApprovalRequestDTO;
import it.unicam.cs.ids.web.requests.company.SubmitApprovalRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * ApprovalRequestService defines the operations related to approval request management.
 */
public interface ApprovalRequestService {
    /**
     * Submits an approval request for a company.
     *
     * @param request the request containing the details of the approval submission
     */
    void submitForApproval(SubmitApprovalRequest request);

    /**
     * Approves an approval request.
     *
     * @param requestId the ID of the request to approve
     * @param adminComments additional comments for the approval
     * @return the updated ApprovalRequestDTO
     */
    ApprovalRequestDTO<Approvable> approve(Long requestId, String adminComments);

    /**
     * Rejects an approval request.
     *
     * @param requestId the ID of the request to reject
     * @param adminComments additional comments for the rejection
     * @return the updated ApprovalRequestDTO
     */
    ApprovalRequestDTO<Approvable> reject(Long requestId, String adminComments);

    /**
     * Retrieves all pending approval requests.
     *
     * @param pageable pagination information
     * @return a page of ApprovalRequestDTO objects
     */
    Page<ApprovalRequestDTO<Approvable>> findPendingRequests(Pageable pageable);
}
