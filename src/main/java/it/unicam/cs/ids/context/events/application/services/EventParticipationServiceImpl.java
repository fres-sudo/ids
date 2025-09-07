package it.unicam.cs.ids.context.events.application.services;

import it.unicam.cs.ids.context.events.application.mappers.EventMapper;
import it.unicam.cs.ids.context.events.application.mappers.EventParticipationMapper;
import it.unicam.cs.ids.context.events.application.factories.ParticipationStrategyFactory;
import it.unicam.cs.ids.context.events.domain.model.Event;
import it.unicam.cs.ids.context.events.domain.model.EventParticipation;
import it.unicam.cs.ids.context.events.domain.repositories.EventParticipationRepository;
import it.unicam.cs.ids.context.events.domain.repositories.EventRepository;
import it.unicam.cs.ids.context.events.application.strategies.ParticipationStrategy;
import it.unicam.cs.ids.context.events.infrastructure.web.dto.EventParticipationDTO;
import it.unicam.cs.ids.shared.application.Finder;
import it.unicam.cs.ids.shared.application.Participable;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class EventParticipationServiceImpl implements EventParticipationService {

    private final EventParticipationRepository participationRepository;
    private final EventRepository eventRepository;
    private final EventParticipationMapper participationMapper;
    private final ParticipationStrategyFactory strategyFactory;
    private final EventMapper eventMapper;

    @Override
    @Transactional
    public <T extends Participable> EventParticipationDTO createParticipationRequest(
            @Nonnull Long eventId, @Nonnull T participant, String applicationMessage, 
            String specialRequirements, String emergencyContact) {
        
        Event event = Finder.findByIdOrThrow(eventRepository, eventId, 
                "Event with id " + eventId + " not found");
        
        Optional<EventParticipation> existingParticipation = participationRepository
                .findByEventIdAndParticipantIdAndParticipantType(
                        eventId,participant.getId(), participant.getParticipantType());
        
        if (existingParticipation.isPresent()) {
            throw new IllegalArgumentException("Participation request already exists for this event and participant");
        }

        ParticipationStrategy<T> strategy = strategyFactory.getStrategy(participant);
        strategy.validateParticipation(participant, event);

        EventParticipation participation = createParticipation(
                event, participant, applicationMessage, specialRequirements, emergencyContact);
        
        EventParticipation savedParticipation = participationRepository.save(participation);

        EventParticipationDTO dto =  participationMapper.toDto(savedParticipation);
        dto.setEvent(eventMapper.toDto(event)); // This is to avoid circular references in DTOs mapper (EventMapper and EventParticipationMapper)

        return dto;
    }

    @Override
    @Transactional
    public EventParticipationDTO updateParticipationRequest(@Nonnull Long participationId,
                                                            String applicationMessage, String specialRequirements, String emergencyContact) {
        
        EventParticipation existingParticipation = Finder.findByIdOrThrow(
                participationRepository, participationId, 
                "Participation with id " + participationId + " not found");
        
        if (existingParticipation.canBeModified()) {
            throw new IllegalArgumentException("Cannot modify participation request in current status");
        }

        existingParticipation.setApplicationMessage(applicationMessage);
        existingParticipation.setSpecialRequirements(specialRequirements);
        existingParticipation.setEmergencyContact(emergencyContact);
        
        EventParticipation savedParticipation = participationRepository.save(existingParticipation);
        return participationMapper.toDto(savedParticipation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventParticipationDTO> getParticipantsByEvent(@Nonnull Long eventId) {
        return participationRepository.findByEventId(eventId).stream()
                .map(participationMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public <T extends Participable> List<EventParticipationDTO> getParticipationsByParticipant(@Nonnull T participant) {
        Long participantId = participant.getId();
        String participantType = participant.getParticipantType();
        System.out.println("Fetching participations for participant ID: " + participantId + ", Type: " + participantType);
        return participationRepository.findByParticipantIdAndParticipantType(
                        participant.getId(), participant.getParticipantType()).stream()
                .map(participationMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public EventParticipationDTO approveParticipation(@Nonnull Long participationId, String responseMessage) {
        EventParticipation participation = Finder.findByIdOrThrow(
                participationRepository, participationId,
                "Participation with id " + participationId + " not found");
        
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
        EventParticipation participation = Finder.findByIdOrThrow(
                participationRepository, participationId,
                "Participation with id " + participationId + " not found");
        
        participation.reject(responseMessage);
        EventParticipation savedParticipation = participationRepository.save(participation);
        return participationMapper.toDto(savedParticipation);
    }

    @Override
    @Transactional
    public void cancelParticipation(@Nonnull Long participationId) {
        EventParticipation participation = Finder.findByIdOrThrow(
                participationRepository, participationId,
                "Participation with id " + participationId + " not found");
        
        if (participation.canBeModified()) {
            throw new IllegalArgumentException("Cannot cancel participation in current status");
        }

        participationRepository.delete(participation);
    }

    private <T extends Participable> EventParticipation createParticipation(
            Event event, T participant, String applicationMessage, 
            String specialRequirements, String emergencyContact) {
        
        EventParticipation participation = new EventParticipation();
        participation.setEvent(event);
        participation.setParticipantId(participant.getId());
        participation.setParticipantType(participant.getParticipantType());
        participation.setParticipantIdentifier(participant.getParticipantIdentifier());
        participation.setApplicationMessage(applicationMessage);
        participation.setSpecialRequirements(specialRequirements);
        participation.setEmergencyContact(emergencyContact);
        
        return participation;
    }

}