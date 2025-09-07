package it.unicam.cs.ids.context.events.application.services;

import it.unicam.cs.ids.context.events.infrastructure.web.dto.EventDTO;

/**
 * Event Service defines the operations related to events.
 * @see EventServiceImpl
 */
public interface EventService {
    
    /**
     * Creates a new event based on the provided request.
     *
     * @param eventDTO the DTO containing event creation details
     * @return the created EventDTO
     */
    EventDTO createEvent(EventDTO eventDTO);

    /**
     * Updates an existing event based on the provided request.
     *
     * @param eventId the ID of the event to be updated
     * @param eventDTO the DTO containing event update details
     * @return the updated EventDTO
     */
    EventDTO updateEvent(Long eventId, EventDTO eventDTO);

    /**
     * Deletes an event by its ID.
     *
     * @param eventId the ID of the event to delete
     */
    void deleteEvent(Long eventId);
}
