package it.unicam.cs.ids.context.events.infrastructure.web;

import it.unicam.cs.ids.context.events.application.services.EventService;
import it.unicam.cs.ids.context.events.infrastructure.web.dto.EventDTO;
import it.unicam.cs.ids.context.company.domain.models.Company;
import it.unicam.cs.ids.context.identity.application.services.AuthService;
import it.unicam.cs.ids.context.identity.domain.model.User;
import it.unicam.cs.ids.context.identity.infrastructure.security.user.AppUserPrincipal;
import it.unicam.cs.ids.shared.infrastructure.web.factories.ApiResponseFactory;
import it.unicam.cs.ids.shared.infrastructure.web.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("events")
public class EventController {

    private final EventService eventService;
    private final ApiResponseFactory responseFactory;

    @PostMapping
    public ApiResponse<EventDTO> createEvent(
            @Valid @RequestBody EventDTO eventDTO
    ) {
        EventDTO createdEvent = eventService.createEvent(eventDTO);
        return responseFactory.createSuccessResponse(
                "Event created successfully",
                createdEvent
        );
    }

    @PutMapping("/{eventId}")
    public ApiResponse<EventDTO> updateEvent(
            @PathVariable Long eventId,
            @Valid @RequestBody EventDTO eventDTO,
            @AuthenticationPrincipal AppUserPrincipal principal
    ) {
        EventDTO updatedEvent = eventService.updateEvent(eventId, eventDTO);
        return responseFactory.createSuccessResponse(
                "Event updated successfully",
                updatedEvent
        );
    }

    @PostMapping("/{eventId}/submit")
    public ApiResponse<EventDTO> submitEventForApproval(
            @PathVariable Long eventId,
            @AuthenticationPrincipal AppUserPrincipal principal
    ) {
        EventDTO event = eventService.submitEventForApproval(eventId);
        return responseFactory.createSuccessResponse(
                "Event submitted for approval successfully",
                event
        );
    }

    @DeleteMapping("/{eventId}")
    public ApiResponse<Void> deleteEvent(
            @PathVariable Long eventId,
            @AuthenticationPrincipal AppUserPrincipal principal
    ) {
        eventService.deleteEvent(eventId);
        return responseFactory.createSuccessResponse(
                "Event deleted successfully",
                null
        );
    }
}
