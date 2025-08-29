package it.unicam.cs.ids.context.events.infrastructure.web;

import it.unicam.cs.ids.context.events.application.services.EventParticipationService;
import it.unicam.cs.ids.context.events.infrastructure.web.dto.EventParticipationDTO;
import it.unicam.cs.ids.context.company.domain.models.Company;
import it.unicam.cs.ids.context.identity.application.services.AuthService;
import it.unicam.cs.ids.context.identity.infrastructure.security.user.AppUserPrincipal;
import it.unicam.cs.ids.shared.infrastructure.web.factories.ApiResponseFactory;
import it.unicam.cs.ids.shared.infrastructure.web.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("event-participations")
public class EventParticipationController {

    private final EventParticipationService participationService;
    private final ApiResponseFactory responseFactory;
    private final AuthService authService;

    @PostMapping
    @PreAuthorize("hasRole('COMPANY')")
    public ApiResponse<EventParticipationDTO> createParticipationRequest(
            @Valid @RequestBody EventParticipationDTO participationDTO,
            @AuthenticationPrincipal AppUserPrincipal principal
    ) {
        Company company = authService.getAuthenticatedCompany();
        participationDTO.getParticipant().setId(company.getId());
        
        EventParticipationDTO createdParticipation = participationService.createParticipationRequest(participationDTO);
        return responseFactory.createSuccessResponse(
                "Participation request created successfully",
                createdParticipation
        );
    }

    @PutMapping("/{participationId}")
    @PreAuthorize("hasRole('COMPANY')")
    public ApiResponse<EventParticipationDTO> updateParticipationRequest(
            @PathVariable Long participationId,
            @Valid @RequestBody EventParticipationDTO participationDTO,
            @AuthenticationPrincipal AppUserPrincipal principal
    ) {
        EventParticipationDTO updatedParticipation = participationService.updateParticipationRequest(participationId, participationDTO);
        return responseFactory.createSuccessResponse(
                "Participation request updated successfully",
                updatedParticipation
        );
    }

    @GetMapping("/{participationId}")
    @PreAuthorize("hasRole('COMPANY')")
    public ApiResponse<EventParticipationDTO> getParticipation(@PathVariable Long participationId) {
        EventParticipationDTO participation = participationService.getParticipationById(participationId);
        return responseFactory.createSuccessResponse(
                "Participation retrieved successfully",
                participation
        );
    }

    @GetMapping("/event/{eventId}")
    @PreAuthorize("hasRole('COMPANY')")
    public ApiResponse<List<EventParticipationDTO>> getParticipationsByEvent(@PathVariable Long eventId) {
        List<EventParticipationDTO> participations = participationService.getParticipationsByEvent(eventId);
        return responseFactory.createSuccessResponse(
                "Event participations retrieved successfully",
                participations
        );
    }

    @GetMapping("/my-participations")
    @PreAuthorize("hasRole('COMPANY')")
    public ApiResponse<List<EventParticipationDTO>> getMyParticipations(@AuthenticationPrincipal AppUserPrincipal principal) {
        Company company = authService.getAuthenticatedCompany();
        List<EventParticipationDTO> participations = participationService.getParticipationsByCompany(company.getId());
        return responseFactory.createSuccessResponse(
                "My participations retrieved successfully",
                participations
        );
    }

    @GetMapping("/pending-requests")
    @PreAuthorize("hasRole('COMPANY')")
    public ApiResponse<List<EventParticipationDTO>> getPendingParticipationRequests(@AuthenticationPrincipal AppUserPrincipal principal) {
        Company company = authService.getAuthenticatedCompany();
        List<EventParticipationDTO> participations = participationService.getPendingParticipationsByOrganizer(company.getId());
        return responseFactory.createSuccessResponse(
                "Pending participation requests retrieved successfully",
                participations
        );
    }

    @PostMapping("/{participationId}/approve")
    @PreAuthorize("hasRole('COMPANY')")
    public ApiResponse<EventParticipationDTO> approveParticipation(
            @PathVariable Long participationId,
            @RequestParam(required = false) String responseMessage,
            @AuthenticationPrincipal AppUserPrincipal principal
    ) {
        EventParticipationDTO participation = participationService.approveParticipation(participationId, responseMessage);
        return responseFactory.createSuccessResponse(
                "Participation approved successfully",
                participation
        );
    }

    @PostMapping("/{participationId}/reject")
    @PreAuthorize("hasRole('COMPANY')")
    public ApiResponse<EventParticipationDTO> rejectParticipation(
            @PathVariable Long participationId,
            @RequestParam(required = false) String responseMessage,
            @AuthenticationPrincipal AppUserPrincipal principal
    ) {
        EventParticipationDTO participation = participationService.rejectParticipation(participationId, responseMessage);
        return responseFactory.createSuccessResponse(
                "Participation rejected successfully",
                participation
        );
    }

    @DeleteMapping("/{participationId}")
    @PreAuthorize("hasRole('COMPANY')")
    public ApiResponse<Void> cancelParticipation(
            @PathVariable Long participationId,
            @AuthenticationPrincipal AppUserPrincipal principal
    ) {
        participationService.cancelParticipation(participationId);
        return responseFactory.createSuccessResponse(
                "Participation cancelled successfully",
                null
        );
    }
}
