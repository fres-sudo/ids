package it.unicam.cs.ids.context.identity.application.services;

import it.unicam.cs.ids.context.identity.domain.model.RequestType;
import it.unicam.cs.ids.context.identity.infrastructure.web.dtos.RoleRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service for handling administrative operations on role requests.
 * Supports unified handling of all role request types (Certifier, Animator, etc.)
 */
public interface AdminService {
    
    /**
     * Approves a role request and updates the user's role.
     *
     * @param requestId the ID of the role request
     * @param comments optional admin comments
     * @return the updated role request DTO
     */
    RoleRequestDTO approve(Long requestId, String comments);
    
    /**
     * Rejects a role request.
     *
     * @param requestId the ID of the role request  
     * @param comments optional admin comments
     * @return the updated role request DTO
     */
    RoleRequestDTO reject(Long requestId, String comments);
    
    /**
     * Finds all pending role requests with optional filtering by request type.
     *
     * @param requestType optional request type filter (null for all types)
     * @param pageable pagination information
     * @return page of pending role requests
     */
    Page<RoleRequestDTO> findPendingRequests(RequestType requestType, Pageable pageable);
}
