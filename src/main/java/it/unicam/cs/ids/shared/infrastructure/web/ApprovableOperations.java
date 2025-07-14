package it.unicam.cs.ids.shared.infrastructure.web;

import it.unicam.cs.ids.shared.application.DTO;
import it.unicam.cs.ids.shared.infrastructure.web.responses.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/approvable")
public interface ApprovableOperations<T extends DTO> {

    @PostMapping("/approve/{requestId}")
    ResponseEntity<ApiResponse<T>> approve(@PathVariable Long requestId, @RequestBody(required = false) String comments);

    @PostMapping("/reject/{requestId}")
    ResponseEntity<ApiResponse<T>> reject(@PathVariable Long requestId, @RequestBody(required = false) String comments);

    @GetMapping()
    ResponseEntity<Page<T>> findPendingRequests(
            @PageableDefault(size = 30) Pageable pageable
    );
}
