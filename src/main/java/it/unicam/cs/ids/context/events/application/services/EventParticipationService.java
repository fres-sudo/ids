package it.unicam.cs.ids.context.events.application.services;

import it.unicam.cs.ids.context.events.infrastructure.web.dto.EventParticipationDTO;

import java.util.List;

/**
 * Event Participation Service defines the operations related to event participations.
 * @see EventParticipationServiceImpl
 */
public interface EventParticipationService {
    
    /**
     * Creates a new participation request for an event.
     *
     * @param participationDTO the DTO containing participation request details
     * @return the created EventParticipationDTO
     */
    EventParticipationDTO createParticipationRequest(EventParticipationDTO participationDTO);

    /**
     * Updates an existing participation request.
     *
     * @param participationId the ID of the participation to update
     * @param participationDTO the DTO containing participation update details
     * @return the updated EventParticipationDTO
     */
    EventParticipationDTO updateParticipationRequest(Long participationId, EventParticipationDTO participationDTO);

    /**
     * Retrieves a participation by its ID.
     *
     * @param participationId the ID of the participation
     * @return the EventParticipationDTO
     */
    EventParticipationDTO getParticipationById(Long participationId);

    /**
     * Retrieves all participations for a specific event.
     *
     * @param eventId the ID of the event
     * @return list of EventParticipationDTOs
     */
    List<EventParticipationDTO> getParticipationsByEvent(Long eventId);

    /**
     * Retrieves all participations for a specific company.
     *
     * @param companyId the ID of the company
     * @return list of EventParticipationDTOs
     */
    List<EventParticipationDTO> getParticipationsByCompany(Long companyId);

    /**
     * Approves a participation request.
     *
     * @param participationId the ID of the participation to approve
     * @param responseMessage optional response message
     * @return the updated EventParticipationDTO
     */
    EventParticipationDTO approveParticipation(Long participationId, String responseMessage);

    /**
     * Rejects a participation request.
     *
     * @param participationId the ID of the participation to reject
     * @param responseMessage optional response message
     * @return the updated EventParticipationDTO
     */
    EventParticipationDTO rejectParticipation(Long participationId, String responseMessage);

    /**
     * Retrieves all pending participation requests for events organized by a company.
     *
     * @param organizerId the ID of the organizer company
     * @return list of EventParticipationDTOs
     */
    List<EventParticipationDTO> getPendingParticipationsByOrganizer(Long organizerId);

    /**
     * Cancels a participation request.
     *
     * @param participationId the ID of the participation to cancel
     */
    void cancelParticipation(Long participationId);
}
