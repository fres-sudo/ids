package it.unicam.cs.ids.context.certification.application.factories;

import it.unicam.cs.ids.context.catalog.domain.model.Bundle;
import it.unicam.cs.ids.context.catalog.domain.repositories.BundleRepository;
import it.unicam.cs.ids.context.certification.application.services.ApprovalRequestService;
import it.unicam.cs.ids.context.certification.domain.model.RequestEntityType;
import it.unicam.cs.ids.context.certification.infrastructure.web.dtos.requests.SubmitApprovalRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BundleApprovalRequestFactory implements ApprovalRequestFactory{

    private static final RequestEntityType REQUEST_ENTITY_TYPE = RequestEntityType.BUNDLE;
    private final ApprovalRequestService approvalRequestService;

    @Override
    public void submit(Long bundleId, Long creatorId) {
        SubmitApprovalRequest approvalRequest = SubmitApprovalRequest.builder()
                .entityId(bundleId)
                .companyId(creatorId)
                .entityType(REQUEST_ENTITY_TYPE)
                .build();
        approvalRequestService.submitForApproval(approvalRequest);
    }
}
