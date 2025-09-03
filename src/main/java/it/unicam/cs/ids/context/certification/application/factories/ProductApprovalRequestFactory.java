package it.unicam.cs.ids.context.certification.application.factories;

import it.unicam.cs.ids.context.certification.application.services.ApprovalRequestService;
import it.unicam.cs.ids.context.certification.domain.model.RequestEntityType;
import it.unicam.cs.ids.context.certification.infrastructure.web.dtos.requests.SubmitApprovalRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductApprovalRequestFactory implements ApprovalRequestFactory{

    private final ApprovalRequestService approvalRequestService;

    @Override
    public void submit(Long productId, Long creatorId) {
        SubmitApprovalRequest approvalRequest = SubmitApprovalRequest.builder()
                .entityId(productId)
                .companyId(creatorId)
                .entityType(RequestEntityType.PRODUCT)
                .build();
        approvalRequestService.submitForApproval(approvalRequest);
    }
}
