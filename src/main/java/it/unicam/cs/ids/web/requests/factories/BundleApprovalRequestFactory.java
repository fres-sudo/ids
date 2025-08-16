package it.unicam.cs.ids.web.requests.factories;

import it.unicam.cs.ids.services.ApprovalRequestService;
import it.unicam.cs.ids.shared.kernel.enums.RequestEntityType;
import it.unicam.cs.ids.web.requests.company.SubmitApprovalRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Factory for creating and submitting approval requests for bundles.
 * This class is responsible for encapsulating the logic to create a
 * {@link SubmitApprovalRequest} for a bundle and submit it for approval.
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BundleApprovalRequestFactory implements ApprovalRequestFactory {
    /** Service to handle approval requests */
    private final ApprovalRequestService approvalRequestService;

    @Override
    public void submit(Long productId, Long creatorId) {
        SubmitApprovalRequest approvalRequest = SubmitApprovalRequest.builder()
                .entityId(productId)
                .companyId(creatorId)
                .entityType(RequestEntityType.BUNDLE)
                .build();
        approvalRequestService.submitForApproval(approvalRequest);
    }
}
