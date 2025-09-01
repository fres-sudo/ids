package it.unicam.cs.ids.context.events.infrastructure.web;

import it.unicam.cs.ids.context.company.domain.models.Company;
import it.unicam.cs.ids.context.company.domain.repositories.CompanyRepository;
import it.unicam.cs.ids.context.events.application.services.EventParticipationService;
import it.unicam.cs.ids.context.events.infrastructure.web.dto.EventParticipationDTO;
import it.unicam.cs.ids.context.events.infrastructure.web.dto.requests.CreateParticipationRequest;
import it.unicam.cs.ids.context.events.infrastructure.web.dto.requests.UpdateParticipationRequest;
import it.unicam.cs.ids.context.identity.domain.model.User;
import it.unicam.cs.ids.context.identity.domain.repositories.UserRepository;
import it.unicam.cs.ids.shared.application.Finder;
import it.unicam.cs.ids.shared.infrastructure.web.factories.ApiResponseFactory;
import it.unicam.cs.ids.shared.infrastructure.web.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events/participations")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EventParticipationController {

    private final ApiResponseFactory responseFactory;
    private final EventParticipationService participationService;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    
    @PostMapping("/company")
    public ApiResponse<EventParticipationDTO> createCompanyParticipation(
            @RequestBody CreateParticipationRequest request) {
        
        Company company = Finder.findByIdOrThrow(companyRepository, request.getParticipantId(),
                "Company with id " + request.getParticipantId() + " not found");
        
        EventParticipationDTO participation = participationService.createParticipationRequest(
                request.getEventId(), company, request.getApplicationMessage(), 
                request.getSpecialRequirements(), request.getEmergencyContact());

        return responseFactory.createSuccessResponse(
                "Participation request created successfully", participation
        );
    }
    
    @PostMapping("/user")
    public ApiResponse<EventParticipationDTO> createUserParticipation(
            @Valid  @RequestBody CreateParticipationRequest request) {
        
        User user = Finder.findByIdOrThrow(userRepository, request.getParticipantId(),
                "User with id " + request.getParticipantId() + " not found");
        
        EventParticipationDTO participation = participationService.createParticipationRequest(
                request.getEventId(), user, request.getApplicationMessage(), 
                request.getSpecialRequirements(), request.getEmergencyContact());

        return responseFactory.createSuccessResponse(
                "Participation request created successfully", participation
        );
    }

    @PutMapping("/{participationId}")
    public ApiResponse<EventParticipationDTO> updateParticipation(
            @PathVariable Long participationId, @RequestBody UpdateParticipationRequest request) {
        
        EventParticipationDTO participation = participationService.updateParticipationRequest(
                participationId, request.getApplicationMessage(), 
                request.getSpecialRequirements(), request.getEmergencyContact());
        return responseFactory.createSuccessResponse(
                "Participation request updated successfully", participation
        );
    }

    @GetMapping("/event/{eventId}")
    public ApiResponse<List<EventParticipationDTO>> getParticipationsByEvent(@PathVariable Long eventId) {
        List<EventParticipationDTO> participants = participationService.getParticipantsByEvent(eventId);
        return responseFactory.createSuccessResponse(
                "Participations retrieved successfully", participants
        );
    }
    
    @GetMapping("/company/{companyId}")
    public ApiResponse<List<EventParticipationDTO>> getCompanyParticipations(@PathVariable Long companyId) {
        Company company = Finder.findByIdOrThrow(companyRepository, companyId,
                "Company with id " + companyId + " not found");

        List<EventParticipationDTO> participants = participationService.getParticipationsByParticipant(company);
        return responseFactory.createSuccessResponse(
                "Participations retrieved successfully", participants
        );
    }
    
    @GetMapping("/user/{userId}")
    public ApiResponse<List<EventParticipationDTO>> getUserParticipations(@PathVariable Long userId) {
        User user = Finder.findByIdOrThrow(userRepository, userId,
                "User with id " + userId + " not found");
        
        List<EventParticipationDTO> participations = participationService.getParticipationsByParticipant(user);
        return responseFactory.createSuccessResponse(
                "Participations retrieved successfully", participations
        );
    }
    
    @PostMapping("/{participationId}/approve")
    public ApiResponse<EventParticipationDTO> approveParticipation(
            @PathVariable Long participationId, @RequestParam(required = false) String responseMessage) {
        
        EventParticipationDTO participation = participationService.approveParticipation(participationId, responseMessage);
        return responseFactory.createSuccessResponse(
                "Participation approved successfully", participation
        );
    }
    
    @PostMapping("/{participationId}/reject")
    public ResponseEntity<EventParticipationDTO> rejectParticipation(
            @PathVariable Long participationId, @RequestParam(required = false) String responseMessage) {
        
        EventParticipationDTO participation = participationService.rejectParticipation(participationId, responseMessage);
        return ResponseEntity.ok(participation);
    }
    
    @GetMapping("/organizer/{organizerId}/pending")
    public ApiResponse<List<EventParticipationDTO>> getPendingParticipantsByOrganizer(@PathVariable Long organizerId) {
        List<EventParticipationDTO> participants = participationService.getPendingParticipantsByOrganizer(organizerId);
        return responseFactory.createSuccessResponse(
                "Pending participations retrieved successfully", participants
        );
    }
    
    @DeleteMapping("/{participationId}")
    public ResponseEntity<Void> cancelParticipation(@PathVariable Long participationId) {
        participationService.cancelParticipation(participationId);
        return ResponseEntity.noContent().build();
    }
}