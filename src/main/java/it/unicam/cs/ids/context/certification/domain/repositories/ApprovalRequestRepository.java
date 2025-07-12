package it.unicam.cs.ids.context.certification.domain.repositories;

import it.unicam.cs.ids.context.certification.domain.model.ApprovalRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApprovalRequestRepository extends JpaRepository<ApprovalRequest, Long>  { }
