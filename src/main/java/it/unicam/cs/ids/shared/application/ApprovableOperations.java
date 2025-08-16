package it.unicam.cs.ids.shared.application;

import it.unicam.cs.ids.dto.DTO;
import it.unicam.cs.ids.shared.infrastructure.api.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

/**
 * Interface defining operations for approving or rejecting requests.
 *
 * @param <T> the type of DTO that represents the request
 */
@RequestMapping("/approvable")
public interface ApprovableOperations<T extends DTO> {
    /**
     * Approves a request.
     *
     * @param requestId the ID of the request to approve
     * @param comments optional comments for the approval
     * @return an ApiResponse containing the approved request
     */
    @PostMapping("/approve/{requestId}")
    ApiResponse<T> approve(@PathVariable Long requestId, @RequestBody(required = false) String comments);

    /**
     * Rejects a request.
     *
     * @param requestId the ID of the request to reject
     * @param comments optional comments for the rejection
     * @return an ApiResponse containing the rejected request
     */
    @PostMapping("/reject/{requestId}")
    ApiResponse<T> reject(@PathVariable Long requestId, @RequestBody(required = false) String comments);

    /**
     * Finds all pending requests for approval.
     *
     * @param pageable pagination information
     * @return a Page of pending requests
     */
    @GetMapping()
    Page<T> findPendingRequests(
            @PageableDefault() Pageable pageable
    );
}
