package it.unicam.cs.ids.repositories;

import it.unicam.cs.ids.models.ApprovalRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApprovalRequestRepository extends JpaRepository<ApprovalRequestEntity, Long>  { }
