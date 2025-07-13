package it.unicam.cs.ids.context.certification.domain.repositories;

import it.unicam.cs.ids.context.certification.infrastructure.web.dtos.requests.CertifierRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing {@link CertifierRequest} entities.
 */
@Repository
public interface CertifierRequestRepository extends JpaRepository<CertifierRequest, Long> {
}
