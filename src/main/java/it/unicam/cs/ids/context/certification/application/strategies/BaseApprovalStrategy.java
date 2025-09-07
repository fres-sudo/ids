package it.unicam.cs.ids.context.certification.application.strategies;

import it.unicam.cs.ids.context.catalog.domain.model.ApprovalStatus;
import it.unicam.cs.ids.context.certification.domain.model.ApprovalRequest;
import it.unicam.cs.ids.context.certification.domain.repositories.ApprovalRequestRepository;
import it.unicam.cs.ids.shared.application.Approvable;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

public abstract class BaseApprovalStrategy<T extends Approvable> implements ApprovalStrategy<T> {

    @Autowired
    private ApprovalRequestRepository approvalRequestRepository;

    @Override
    public void validateEntityState(T entity) {
        ApprovalStatus currentStatus = entity.getStatus();
        if (currentStatus == ApprovalStatus.PENDING) {
            throw new IllegalStateException("Entity is already pending approval");
        }
        if (currentStatus == ApprovalStatus.APPROVED) {
            throw new IllegalStateException("Entity is already approved");
        }
    }

    @Override
    public void setStatus(T entity, ApprovalStatus status) {
        entity.setApprovalStatus(status);
    }

    @Override
    public void performAdditionalValidation(T entity) {
        ApprovalStatus currentStatus = entity.getStatus();
        LocalDateTime lastUpdatedAt = entity.getUpdatedAt();
        Long entityId = entity.getId();


        // check if the entity has been updated since the last submission (check updatedAt field)
        if (currentStatus == ApprovalStatus.REJECTED) {
            // find all the request with the given entityId
            List<ApprovalRequest> requests = approvalRequestRepository.findByEntityIdOrderBySubmittedAtDesc(entityId);
            if (!requests.isEmpty()) { // there is at least one previous request in addition to the current one
                ApprovalRequest latestRequest = requests.getFirst(); // get the latest request
                if (latestRequest.getProcessedAt().isAfter(lastUpdatedAt)) { // entity has not been updated since the last rejection
                    throw new IllegalStateException("Entity has not been updated since the last rejection");
                }
            }
        }
    }
}