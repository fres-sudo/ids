package it.unicam.cs.ids.context.certification.application.strategies;

import it.unicam.cs.ids.context.catalog.domain.model.ApprovalStatus;
import it.unicam.cs.ids.context.certification.domain.model.ApprovalRequest;

public interface ApprovalStrategy<T> {
    T findEntity(Long entityId);
    void validateOwnership(T entity, Long requesterId);
    void validateEntityState(T entity);
    void performAdditionalValidation(T entity);
    void setStatus(T entity, ApprovalStatus status);
    T saveEntity(T entity);
    ApprovalRequest createApprovalRequest(Long entityId, Long requesterId);
}