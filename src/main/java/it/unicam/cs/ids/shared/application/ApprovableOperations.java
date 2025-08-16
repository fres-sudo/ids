package it.unicam.cs.ids.shared.application;

import it.unicam.cs.ids.dto.DTO;
import it.unicam.cs.ids.shared.infrastructure.api.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/approvable")
public interface ApprovableOperations<T extends DTO> {

    @PostMapping("/approve/{requestId}")
    ApiResponse<T> approve(@PathVariable Long requestId, @RequestBody(required = false) String comments);

    @PostMapping("/reject/{requestId}")
    ApiResponse<T> reject(@PathVariable Long requestId, @RequestBody(required = false) String comments);

    @GetMapping()
    Page<T> findPendingRequests(
            @PageableDefault() Pageable pageable
    );
}
