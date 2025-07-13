package it.unicam.cs.ids.context.identity.application.services;


import it.unicam.cs.ids.context.certification.infrastructure.web.dtos.CertifierRequestDTO;
import it.unicam.cs.ids.shared.application.BaseRequest;
import it.unicam.cs.ids.shared.application.DTO;
import org.springframework.data.domain.Page;

public interface AdminService {
    CertifierRequestDTO approve(Long requestId, String comments);
    CertifierRequestDTO reject(Long requestId, String comments);
    Page<CertifierRequestDTO> findPendingRequests(Integer pageNo, Integer pageSize, String sortBy);
}
