package it.unicam.cs.ids.context.identity.domain.repositories;

import it.unicam.cs.ids.context.catalog.domain.model.ApprovalStatus;
import it.unicam.cs.ids.context.identity.domain.model.RequestType;
import it.unicam.cs.ids.context.identity.domain.model.RoleRequest;
import it.unicam.cs.ids.context.identity.domain.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRequestRepository extends JpaRepository<RoleRequest, Long> {
    
    /**
     * Finds all pending role requests of a specific type.
     *
     * @param requestType the type of role request to filter by
     * @param pageable pagination information
     * @return page of pending role requests of the specified type
     */
    Page<RoleRequest> findByRequestTypeAndStatus(RequestType requestType, ApprovalStatus status, Pageable pageable);
    
    /**
     * Finds approved role request for a user and request type to check approval status during login.
     */
    Optional<RoleRequest> findByRequestingUserAndRequestTypeAndStatus(User user, RequestType requestType, ApprovalStatus status);
}