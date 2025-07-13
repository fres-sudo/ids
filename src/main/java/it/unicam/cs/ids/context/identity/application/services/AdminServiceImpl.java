package it.unicam.cs.ids.context.identity.application.services;

import it.unicam.cs.ids.context.catalog.domain.model.ApprovalStatus;
import it.unicam.cs.ids.context.certification.application.mappers.CertifierMapper;
import it.unicam.cs.ids.context.certification.domain.repositories.CertifierRequestRepository;
import it.unicam.cs.ids.context.certification.infrastructure.web.dtos.CertifierRequestDTO;
import it.unicam.cs.ids.context.certification.infrastructure.web.dtos.requests.CertifierRequest;
import it.unicam.cs.ids.context.identity.domain.model.PlatformRoles;
import it.unicam.cs.ids.context.identity.domain.model.User;
import it.unicam.cs.ids.context.identity.domain.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;
    private final CertifierRequestRepository certifierRequestRepository;
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
    public Page<CertifierRequestDTO> findPendingRequests(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<CertifierRequest> requests = certifierRequestRepository.findAll(paging);
        return requests.map(certifierMapper::toDto);
    }

    private CertifierRequestDTO processApprovalRequest(Long requestId, ApprovalStatus newStatus, String comments) {
        CertifierRequest certifierRequest = certifierRequestRepository.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException("Request not found with id: " + requestId));

        if (certifierRequest.getStatus() != ApprovalStatus.PENDING) {
            throw new IllegalStateException("Only pending requests can be processed");
        }

        // Update user
        User user = userRepository.findById(certifierRequest.getRequestingUser().getId()).orElseThrow(
                () -> {
                    certifierRequest.setStatus(ApprovalStatus.REJECTED);
                    return new IllegalArgumentException("User not found");
                }
        );
        user.setRole(PlatformRoles.CERTIFIER);
        userRepository.save(user);

        // Update certifier request
        certifierRequest.setStatus(newStatus);
        certifierRequest.setComments(comments);
        CertifierRequest savedRequest = certifierRequestRepository.save(certifierRequest);
        // TODO: notify user with emails or something if necessary
        return certifierMapper.toDto(savedRequest);
    }
}
