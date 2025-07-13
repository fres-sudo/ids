package it.unicam.cs.ids.shared.infrastructure.web;

import it.unicam.cs.ids.shared.application.DTO;
import it.unicam.cs.ids.shared.infrastructure.web.responses.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/approvable")
public interface ApprovableOperations<T extends DTO> {

    @GetMapping("/approve/{requestId}")
    ResponseEntity<ApiResponse<T>> approve(@PathVariable Long requestId, @RequestBody(required = false) String comments);

    @PreAuthorize("hasRole('CERTIFIER')")
    @GetMapping("/reject/{requestId}")
    ResponseEntity<ApiResponse<T>> reject(@PathVariable Long requestId, @RequestBody(required = false) String comments);

    @GetMapping("/")
    ResponseEntity<Page<T>> findPendingRequests(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy
    );
}
