package it.unicam.cs.ids.repositories;

import it.unicam.cs.ids.web.requests.certifier.ApprovalRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApprovalRequestRepository extends JpaRepository<ApprovalRequest, Long>  { }
