package it.unicam.cs.ids.services.implementation;

import it.unicam.cs.ids.models.CertifierRequestEntity;
import it.unicam.cs.ids.services.AdminService;
import it.unicam.cs.ids.shared.kernel.enums.ApprovalStatus;
import it.unicam.cs.ids.mappers.CertifierMapper;
import it.unicam.cs.ids.repositories.CertifierRequestRepository;
import it.unicam.cs.ids.dto.CertifierRequestDTO;
import it.unicam.cs.ids.shared.kernel.enums.PlatformRoles;
import it.unicam.cs.ids.models.User;
import it.unicam.cs.ids.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Implementation of the AdminService interface that handles certifier requests.
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AdminServiceImpl implements AdminService {
    /** Repository for accessing user data */
    private final UserRepository userRepository;
    /** Repository for accessing certifier requests */
    private final CertifierRequestRepository certifierRequestRepository;
    /** Mapper for converting CertifierRequest to CertifierRequestDTO */
    private final CertifierMapper certifierMapper;

    @Override
    public CertifierRequestDTO approve(Long requestId, String comments) {
        return processApprovalRequest(requestId, ApprovalStatus.APPROVED, comments);
    }

    @Override
    public CertifierRequestDTO reject(Long requestId, String comments) {
        return processApprovalRequest(requestId, ApprovalStatus.REJECTED, comments);
    }

    @Override
    public Page<CertifierRequestDTO> findPendingRequests(Pageable pageable) {
        Page<CertifierRequestEntity> requests = certifierRequestRepository.findAll(pageable);
        return requests.map(certifierMapper::toDto);
    }

    /**
     * Processes an approval or rejection request for a certifier request.
     *
     * @param requestId the ID of the certifier request
     * @param newStatus the new status to set (APPROVED or REJECTED)
     * @param comments additional comments for the request
     * @return the updated CertifierRequestDTO
     */
    private CertifierRequestDTO processApprovalRequest(Long requestId, ApprovalStatus newStatus, String comments) {
        CertifierRequestEntity certifierRequest = certifierRequestRepository.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException("Request not found with id: " + requestId));

        if (certifierRequest.getStatus() != ApprovalStatus.PENDING) {
            throw new IllegalStateException("Only pending requests can be processed");
        }

        // If approved, update the user role
        if (newStatus == ApprovalStatus.APPROVED) {
            User user = userRepository.findById(certifierRequest.getRequestingUser().getId())
                    .orElseThrow(() -> {
                        certifierRequest.setStatus(ApprovalStatus.REJECTED);
                        return new IllegalArgumentException("User not found");
                    });
            user.setRole(PlatformRoles.CERTIFIER);
            userRepository.save(user);
        }

        // Update certifier request
        certifierRequest.setStatus(newStatus);
        certifierRequest.setComments(comments);
        CertifierRequestEntity savedRequest = certifierRequestRepository.save(certifierRequest);

        // TODO: notify user with emails or something if necessary
        return certifierMapper.toDto(savedRequest);
    }

}
