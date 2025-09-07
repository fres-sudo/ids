package it.unicam.cs.ids.context.certification.application.services;

import it.unicam.cs.ids.context.catalog.domain.model.ApprovalStatus;
import it.unicam.cs.ids.context.certification.application.mappers.ApprovalRequestMapper;
import it.unicam.cs.ids.context.certification.application.strategies.ApprovalStrategy;
import it.unicam.cs.ids.context.certification.domain.model.ApprovalRequest;
import it.unicam.cs.ids.context.certification.domain.model.RequestEntityType;
import it.unicam.cs.ids.context.certification.domain.repositories.ApprovalRequestRepository;
import it.unicam.cs.ids.context.certification.infrastructure.web.dtos.ApprovalRequestDTO;
import it.unicam.cs.ids.shared.application.Approvable;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SubmissionService {

    private final ApprovalRequestRepository approvalRequestRepository;
    private final Map<RequestEntityType, ApprovalStrategy<?>> strategies;
    private final ApprovalRequestMapper approvalRequestMapper;

    @SuppressWarnings("unchecked")
    @Transactional
    public <T> ApprovalRequestDTO<Approvable> submitForApproval(RequestEntityType entityType, Long entityId, Long requesterId) {
        ApprovalStrategy<T> strategy = (ApprovalStrategy<T>) strategies.get(entityType);
        if (strategy == null) {
            throw new IllegalArgumentException("No strategy found for entity type: " + entityType);
        }

        // Template method flow
        T entity = strategy.findEntity(entityId);
        strategy.validateOwnership(entity, requesterId);
        strategy.validateEntityState(entity);
        strategy.performAdditionalValidation(entity);

        strategy.setStatus(entity, ApprovalStatus.PENDING);
        strategy.saveEntity(entity);

        ApprovalRequest request = strategy.createApprovalRequest(entityId, requesterId);
        request.setStatus(ApprovalStatus.PENDING);
        approvalRequestRepository.save(request);
        return approvalRequestMapper.toDto(request);
    }
}