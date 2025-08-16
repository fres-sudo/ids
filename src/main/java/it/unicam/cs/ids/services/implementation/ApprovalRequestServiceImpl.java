package it.unicam.cs.ids.services.implementation;

import it.unicam.cs.ids.services.ApprovalRequestService;
import it.unicam.cs.ids.shared.kernel.enums.ApprovalStatus;
import it.unicam.cs.ids.model.Bundle;
import it.unicam.cs.ids.model.Product;
import it.unicam.cs.ids.repositories.BundleRepository;
import it.unicam.cs.ids.repositories.ProductRepository;
import it.unicam.cs.ids.mappers.ApprovalRequestMapper;
import it.unicam.cs.ids.shared.application.Approvable;
import it.unicam.cs.ids.web.requests.certifier.ApprovalRequest;
import it.unicam.cs.ids.shared.kernel.enums.RequestEntityType;
import it.unicam.cs.ids.repositories.ApprovalRequestRepository;
import it.unicam.cs.ids.dto.ApprovalRequestDTO;
import it.unicam.cs.ids.web.requests.company.SubmitApprovalRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the {@link ApprovalRequestService} interface that handles approval requests for products and bundles.
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
@Transactional
public class ApprovalRequestServiceImpl implements ApprovalRequestService {
    /** Repository for accessing approval requests */
    final ApprovalRequestRepository approvalRequestRepository;
    /** Repository for accessing products */
    final ProductRepository productRepository;
    /** Repository for accessing bundles */
    final BundleRepository bundleRepository;
    /** Mapper for converting ApprovalRequest to ApprovalRequestDTO */
    final ApprovalRequestMapper approvalRequestMapper;

    @Override
    public void submitForApproval(SubmitApprovalRequest request) {
        //TODO check if another request with the same info exists
        ApprovalRequest approvalRequest = approvalRequestMapper.fromSubmitRequest(request);
        ApprovalRequest entity = approvalRequestRepository.save(approvalRequest);
        // TODO notify certifier if necessary
        approvalRequestMapper.toDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public ApprovalRequestDTO<Approvable> approve(Long requestId, String adminComments) {
        return processApprovalRequest(requestId, ApprovalStatus.APPROVED, adminComments);
    }

    @Override
    @Transactional(readOnly = true)
    public ApprovalRequestDTO<Approvable> reject(Long requestId, String adminComments) {
        return processApprovalRequest(requestId, ApprovalStatus.REJECTED, adminComments);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ApprovalRequestDTO<Approvable>> findPendingRequests(Pageable pageable) {
        Page<ApprovalRequest> requests = approvalRequestRepository.findAll(pageable);
        return requests.map(approvalRequestMapper::toDto);
    }

    protected ApprovalRequestDTO<Approvable> processApprovalRequest(Long requestId, ApprovalStatus newStatus, String adminComments) {
        ApprovalRequest approvalRequest = approvalRequestRepository.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException("Request not found with id: " + requestId));

        if (approvalRequest.getStatus() != ApprovalStatus.PENDING) {
            throw new IllegalStateException("Only pending requests can be processed");
        }

        // Update entity
        Approvable entity = findEntity(approvalRequest.getEntityType(), approvalRequest.getEntityId());
        entity.setApprovalStatus(newStatus);
        saveEntity(entity, approvalRequest.getEntityType());

        // Update approval request
        approvalRequest.setStatus(newStatus);
        approvalRequest.setComments(adminComments);
        ApprovalRequest savedRequest = approvalRequestRepository.save(approvalRequest);
        // TODO: notify company with emails or something if necessary
        return approvalRequestMapper.toDto(savedRequest);
    }

    private Approvable findEntity(RequestEntityType entityType, Long entityId) {
        return switch (entityType) {
            case PRODUCT -> productRepository.findById(entityId)
                    .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + entityId));
            case BUNDLE -> bundleRepository.findById(entityId)
                    .orElseThrow(() -> new EntityNotFoundException("Bundle not found with id: " + entityId));
        };
    }

    private void saveEntity(Approvable entity, RequestEntityType entityType) {
        switch (entityType) {
            case PRODUCT -> productRepository.save((Product) entity);
            case BUNDLE -> bundleRepository.save((Bundle) entity);
        }
    }
}
