package it.unicam.cs.ids.services;


import it.unicam.cs.ids.dto.CertifierRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminService {
    CertifierRequestDTO approve(Long requestId, String comments);
    CertifierRequestDTO reject(Long requestId, String comments);
    Page<CertifierRequestDTO> findPendingRequests( Pageable pageable);
}
