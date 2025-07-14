package it.unicam.cs.ids.context.identity.domain.repositories;

import it.unicam.cs.ids.context.identity.domain.model.CertifierRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link CertifierRequest} entities.
 */
@Repository
public interface CertifierRequestRepository extends JpaRepository<CertifierRequest, Long> {
}
