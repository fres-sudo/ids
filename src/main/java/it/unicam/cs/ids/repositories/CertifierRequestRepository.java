package it.unicam.cs.ids.repositories;

import it.unicam.cs.ids.web.requests.certifier.CertifierRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link CertifierRequest} entities.
 */
@Repository
public interface CertifierRequestRepository extends JpaRepository<CertifierRequest, Long> {
}
