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
    /**
     * Finds a {@link CertifierRequest} by its user ID.
     *
     * @param userId the ID of the user associated with the requests
     * @return an Optional containing the CertifierRequest if found, otherwise empty
     */
     Optional<CertifierRequest> findByUserId(Long userId);

    /**
     * Checks if a {@link CertifierRequest} exists for a given user ID.
     *
     * @param userId the ID of the user to check
     * @return true if a CertifierRequest exists for the user, false otherwise
     */
     boolean existsByUserId(Long userId);
}
