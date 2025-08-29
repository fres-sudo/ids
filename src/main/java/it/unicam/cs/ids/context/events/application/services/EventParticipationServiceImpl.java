package it.unicam.cs.ids.context.events.application.services;

import it.unicam.cs.ids.context.catalog.domain.model.ApprovalStatus;
import it.unicam.cs.ids.context.events.application.mappers.EventParticipationMapper;
import it.unicam.cs.ids.context.events.domain.model.Event;
import it.unicam.cs.ids.context.events.domain.model.EventParticipation;
import it.unicam.cs.ids.context.events.domain.repositories.EventParticipationRepository;
import it.unicam.cs.ids.context.events.domain.repositories.EventRepository;
import it.unicam.cs.ids.context.events.infrastructure.web.dto.EventParticipationDTO;
import it.unicam.cs.ids.shared.application.Finder;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of {@link EventParticipationService},
 * This service handles the management of event participation requests.
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class EventParticipationServiceImpl implements EventParticipationService {

    private final EventParticipationMapper participationMapper;
    private final EventParticipationRepository participationRepository;
    private final EventRepository eventRepository;

    @Override
    @Transactional
    public EventParticipationDTO createParticipationRequest(@Nonnull EventParticipationDTO participationDTO) {
        // Check if participation already exists
        Optional<EventParticipation> existingParticipation = participationRepository
                .findByEventIdAndParticipantId(participationDTO.getEvent().getId(), 
                                             participationDTO.getParticipant().getId());
        
        if (existingParticipation.isPresent()) {
            throw new IllegalArgumentException("Participation request already exists for this event and company");
        }

        // Validate event is available for registration
        Event event = Finder.findByIdOrThrow(eventRepository, participationDTO.getEvent().getId(),
                "Event with id " + participationDTO.getEvent().getId() + " not found");
        
        if (!event.isRegistrationOpen()) {
            throw new IllegalArgumentException("Registration is closed for this event");
        }
        
        if (!event.hasAvailableSlots()) {
            throw new IllegalArgumentException("No available slots for this event");
        }

        EventParticipation participation = participationMapper.fromDto(participationDTO);
        EventParticipation savedParticipation = participationRepository.save(participation);
        return participationMapper.toDto(savedParticipation);
    }

    @Override
    @Transactional
    public EventParticipationDTO updateParticipationRequest(@Nonnull Long participationId, @Nonnull EventParticipationDTO participationDTO) {
        EventParticipation existingParticipation = Finder.findByIdOrThrow(participationRepository, participationId,
                "Participation with id " + participationId + " not found");
        
        if (!existingParticipation.canBeModified()) {
            throw new IllegalArgumentException("Cannot modify participation request in current status");
        }

        EventParticipation updatedParticipation = participationMapper.updateParticipationFromDto(participationDTO, existingParticipation);
        EventParticipation savedParticipation = participationRepository.save(updatedParticipation);
        return participationMapper.toDto(savedParticipation);
    }

    @Override
    @Transactional(readOnly = true)
    public EventParticipationDTO getParticipationById(@Nonnull Long participationId) {
        EventParticipation participation = Finder.findByIdOrThrow(participationRepository, participationId,
                "Participation with id " + participationId + " not found");
        return participationMapper.toDto(participation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventParticipationDTO> getParticipationsByEvent(@Nonnull Long eventId) {
        return participationRepository.findByEventId(eventId).stream()
                .map(participationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventParticipationDTO> getParticipationsByCompany(@Nonnull Long companyId) {
        return participationRepository.findByParticipantId(companyId).stream()
                .map(participationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EventParticipationDTO approveParticipation(@Nonnull Long participationId, String responseMessage) {
        EventParticipation participation = Finder.findByIdOrThrow(participationRepository, participationId,
                "Participation with id " + participationId + " not found");
        
        // Check if event still has available slots
        if (!participation.getEvent().hasAvailableSlots()) {
            throw new IllegalArgumentException("No available slots remaining for this event");
        }

        participation.approve(responseMessage);
        EventParticipation savedParticipation = participationRepository.save(participation);
        return participationMapper.toDto(savedParticipation);
    }

    @Override
    @Transactional
    public EventParticipationDTO rejectParticipation(@Nonnull Long participationId, String responseMessage) {
        EventParticipation participation = Finder.findByIdOrThrow(participationRepository, participationId,
                "Participation with id " + participationId + " not found");
        
        participation.reject(responseMessage);
        EventParticipation savedParticipation = participationRepository.save(participation);
        return participationMapper.toDto(savedParticipation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventParticipationDTO> getPendingParticipationsByOrganizer(@Nonnull Long organizerId) {
        return participationRepository.findPendingParticipationsByOrganizer(organizerId).stream()
                .map(participationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void cancelParticipation(@Nonnull Long participationId) {
        EventParticipation participation = Finder.findByIdOrThrow(participationRepository, participationId,
                "Participation with id " + participationId + " not found");
        
        if (!participation.canBeModified()) {
            throw new IllegalArgumentException("Cannot cancel participation in current status");
        }

        participationRepository.delete(participation);
    }
}
