package it.unicam.cs.ids.context.certification.application.services;

import it.unicam.cs.ids.context.catalog.domain.model.ApprovalStatus;
import it.unicam.cs.ids.context.catalog.domain.model.Bundle;
import it.unicam.cs.ids.context.catalog.domain.model.Product;
import it.unicam.cs.ids.context.catalog.domain.repositories.BundleRepository;
import it.unicam.cs.ids.context.catalog.domain.repositories.ProductRepository;
import it.unicam.cs.ids.context.certification.application.mappers.ApprovalRequestMapper;
import it.unicam.cs.ids.context.events.domain.model.Event;
import it.unicam.cs.ids.context.events.domain.repositories.EventRepository;
import it.unicam.cs.ids.shared.application.Approvable;
import it.unicam.cs.ids.context.certification.domain.model.ApprovalRequest;
import it.unicam.cs.ids.context.certification.domain.model.RequestEntityType;
import it.unicam.cs.ids.context.certification.domain.repositories.ApprovalRequestRepository;
import it.unicam.cs.ids.context.certification.infrastructure.web.dtos.ApprovalRequestDTO;
import it.unicam.cs.ids.context.certification.infrastructure.web.dtos.requests.SubmitApprovalRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import static it.unicam.cs.ids.context.certification.application.mappers.ApprovalRequestMapper.getApprovable;


@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
@Transactional
public class ApprovalRequestServiceImpl implements ApprovalRequestService {

    final ApprovalRequestRepository approvalRequestRepository;
    final ApprovalRequestMapper approvalRequestMapper;

    final ProductRepository productRepository;
    final BundleRepository bundleRepository;
    final EventRepository eventRepository;

    @Override
    @Transactional()
    public ApprovalRequestDTO<Approvable> approve(Long requestId, String adminComments) {
        return processApprovalRequest(requestId, ApprovalStatus.APPROVED, adminComments);
    }

    @Override
    @Transactional()
    public ApprovalRequestDTO<Approvable> reject(Long requestId, String adminComments) {
        return processApprovalRequest(requestId, ApprovalStatus.REJECTED, adminComments);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ApprovalRequestDTO<Approvable>> findPendingRequests(Pageable pageable) {
        Page<ApprovalRequest> requests = approvalRequestRepository.findAllByStatusOrderBySubmittedAtDesc(ApprovalStatus.PENDING, pageable);
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
        return getApprovable(entityType, entityId, productRepository, bundleRepository, eventRepository);
    }

    private void saveEntity(Approvable entity, RequestEntityType entityType) {
        switch (entityType) {
            case PRODUCT -> productRepository.save((Product) entity);
            case BUNDLE -> bundleRepository.save((Bundle) entity);
            case EVENT -> eventRepository.save((Event) entity);
        }
    }
}
