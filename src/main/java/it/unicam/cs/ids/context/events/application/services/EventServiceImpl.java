package it.unicam.cs.ids.context.events.application.services;

import it.unicam.cs.ids.context.catalog.domain.model.ApprovalStatus;
import it.unicam.cs.ids.context.events.application.mappers.EventMapper;
import it.unicam.cs.ids.context.events.domain.model.Event;
import it.unicam.cs.ids.context.events.domain.repositories.EventRepository;
import it.unicam.cs.ids.context.events.infrastructure.web.dto.EventDTO;
import it.unicam.cs.ids.shared.application.Finder;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of {@link EventService},
 * This service handles the creation and management of event related entities.
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class EventServiceImpl implements EventService {

    private final EventMapper eventMapper;
    private final EventRepository eventRepository;

    @Override
    @Transactional
    public EventDTO createEvent(@Nonnull EventDTO eventDTO) {
        Event event = eventMapper.fromDto(eventDTO);
        Event savedEvent = eventRepository.save(event);
        return eventMapper.toDto(savedEvent);
    }

    @Override
    @Transactional
    public EventDTO updateEvent(@Nonnull Long eventId, @Nonnull EventDTO eventDTO) {
        Event existingEvent = Finder.findByIdOrThrow(eventRepository, eventId,
                "Event with id " + eventId + " not found");

        Event updatedEvent = eventMapper.updateEventFromDto(eventDTO, existingEvent);
        Event savedEvent = eventRepository.save(updatedEvent);
        return eventMapper.toDto(savedEvent);
    }

    @Override
    @Transactional
    public void deleteEvent(@Nonnull Long eventId) {
        Event event = Finder.findByIdOrThrow(eventRepository, eventId,
                "Event with id " + eventId + " not found");
        eventRepository.delete(event);
    }

    @Override
    @Transactional
    public EventDTO submitEventForApproval(@Nonnull Long eventId) {
        Event event = Finder.findByIdOrThrow(eventRepository, eventId,
                "Event with id " + eventId + " not found");
        
        event.setApprovalStatus(ApprovalStatus.PENDING);
        // TODO: Add logic to notify the admin or relevant parties about the submission request
        Event savedEvent = eventRepository.save(event);
        return eventMapper.toDto(savedEvent);
    }
}
