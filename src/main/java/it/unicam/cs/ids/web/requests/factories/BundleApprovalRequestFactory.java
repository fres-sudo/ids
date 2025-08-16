package it.unicam.cs.ids.web.requests.factories;

import it.unicam.cs.ids.services.ApprovalRequestService;
import it.unicam.cs.ids.shared.kernel.enums.RequestEntityType;
import it.unicam.cs.ids.web.requests.company.SubmitApprovalRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BundleApprovalRequestFactory implements ApprovalRequestFactory {

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
