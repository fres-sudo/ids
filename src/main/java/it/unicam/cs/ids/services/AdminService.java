package it.unicam.cs.ids.services;


import it.unicam.cs.ids.dto.CertifierRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * AdminService defines the operations related to certifier request management.
 */
public interface AdminService {
    /**
     * Approves a certifier request.
     *
     * @param requestId the ID of the request to approve
     * @param comments additional comments for the approval
     * @return the updated CertifierRequestDTO
     */
    CertifierRequestDTO approve(Long requestId, String comments);

    /**
     * Rejects a certifier request.
     *
     * @param requestId the ID of the request to reject
     * @param comments additional comments for the rejection
     * @return the updated CertifierRequestDTO
     */
    CertifierRequestDTO reject(Long requestId, String comments);

    /**
     * Retrieves all pending certifier requests.
     *
     * @param pageable pagination information
     * @return a page of CertifierRequestDTO objects
     */
    Page<CertifierRequestDTO> findPendingRequests(Pageable pageable);
}
