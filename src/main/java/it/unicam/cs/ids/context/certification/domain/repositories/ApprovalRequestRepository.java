package it.unicam.cs.ids.context.certification.domain.repositories;

import it.unicam.cs.ids.context.catalog.domain.model.ApprovalStatus;
import it.unicam.cs.ids.context.certification.domain.model.ApprovalRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface ApprovalRequestRepository extends JpaRepository<ApprovalRequest, Long>  {
    List<ApprovalRequest> findByEntityIdOrderBySubmittedAtDesc(Long entityId);
    Page<ApprovalRequest> findAllByStatusOrderBySubmittedAtDesc(ApprovalStatus status, Pageable pageable);
}
