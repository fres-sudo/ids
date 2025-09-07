package it.unicam.cs.ids.context.certification.application.factories;

import it.unicam.cs.ids.context.certification.application.services.ApprovalRequestService;
import it.unicam.cs.ids.context.certification.domain.model.RequestEntityType;
import it.unicam.cs.ids.context.certification.infrastructure.web.dtos.requests.SubmitApprovalRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EventApprovalRequestFactory implements ApprovalRequestFactory {

    private static final RequestEntityType REQUEST_ENTITY_TYPE = RequestEntityType.EVENT;
    private final ApprovalRequestService approvalRequestService;

    @Override
    public void submit(Long eventId, Long creatorId) {
        SubmitApprovalRequest approvalRequest = SubmitApprovalRequest.builder()
                .entityId(eventId)
                .userId(creatorId)
                .entityType(REQUEST_ENTITY_TYPE)
                .build();
        approvalRequestService.submitForApproval(approvalRequest);
    }
}