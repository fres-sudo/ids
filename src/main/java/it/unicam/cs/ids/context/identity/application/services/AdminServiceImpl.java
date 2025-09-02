package it.unicam.cs.ids.context.identity.application.services;

import it.unicam.cs.ids.context.catalog.domain.model.ApprovalStatus;
import it.unicam.cs.ids.context.identity.application.mappers.RoleRequestMapper;
import it.unicam.cs.ids.context.identity.domain.model.RequestType;
import it.unicam.cs.ids.context.identity.domain.repositories.RoleRequestRepository;
import it.unicam.cs.ids.context.identity.infrastructure.web.dtos.RoleRequestDTO;
import it.unicam.cs.ids.context.identity.domain.model.RoleRequest;
import it.unicam.cs.ids.context.identity.domain.model.User;
import it.unicam.cs.ids.context.identity.domain.repositories.UserRepository;
import it.unicam.cs.ids.shared.kernel.exceptions.auth.NotFound;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Implementation of AdminService that handles unified role requests.
 * Follows the Single Responsibility and Open/Closed principles.
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;
    private final RoleRequestRepository roleRequestRepository;
    private final RoleRequestMapper roleRequestMapper;


    @Override
    public RoleRequestDTO approve(Long requestId, String comments) {
        return processApprovalRequest(requestId, ApprovalStatus.APPROVED, comments);
    }

    @Override
    public RoleRequestDTO reject(Long requestId, String comments) {
        return processApprovalRequest(requestId, ApprovalStatus.REJECTED, comments);
    }

    @Override
    public Page<RoleRequestDTO> findPendingRequests(RequestType requestType, Pageable pageable) {
        Page<RoleRequest> requests;
        
        if (requestType != null) {
            // Filter by specific request type
            requests = roleRequestRepository.findByRequestTypeAndStatus(requestType, ApprovalStatus.PENDING, pageable);
        } else {
            // Get all pending requests regardless of type
            requests = roleRequestRepository.findAll(pageable);
        }
        
        return requests.map(roleRequestMapper::toDto);
    }

    private RoleRequestDTO processApprovalRequest(Long requestId, ApprovalStatus newStatus, String comments) {
        RoleRequest roleRequest = roleRequestRepository.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException("Request not found with id: " + requestId));

        if (roleRequest.getStatus() != ApprovalStatus.PENDING) {
            throw new IllegalStateException("Only pending requests can be processed");
        }

        // If approved, update the user role
        if (newStatus == ApprovalStatus.APPROVED) {
            User user = userRepository.findById(roleRequest.getRequestingUser().getId())
                    .orElseThrow(() -> {
                        roleRequest.setStatus(ApprovalStatus.REJECTED);
                        return new NotFound("User not found");
                    });
            
            // Set the appropriate role based on request type
            user.setRole(roleRequest.getRequestType().getTargetRole());
            userRepository.save(user);
        }

        // Update role request
        roleRequest.setStatus(newStatus);
        roleRequest.setComments(comments);
        RoleRequest savedRequest = roleRequestRepository.save(roleRequest);
        
        // TODO: notify user with emails or something if necessary
        return roleRequestMapper.toDto(savedRequest);
    }
}
