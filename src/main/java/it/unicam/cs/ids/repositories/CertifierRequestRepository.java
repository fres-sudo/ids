package it.unicam.cs.ids.repositories;

import it.unicam.cs.ids.models.CertifierRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link CertifierRequestEntity} entities.
 */
@Repository
public interface CertifierRequestRepository extends JpaRepository<CertifierRequestEntity, Long> {
}
