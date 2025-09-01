package it.unicam.cs.ids.context.events.application.services;

import it.unicam.cs.ids.context.events.infrastructure.web.dto.EventParticipationDTO;
import it.unicam.cs.ids.shared.application.Participable;

import java.util.List;

public interface EventParticipationService {
    
    <T extends Participable> EventParticipationDTO createParticipationRequest(
            Long eventId, T participant, String applicationMessage, String specialRequirements, String emergencyContact);
    
    EventParticipationDTO updateParticipationRequest(Long participationId, String applicationMessage,
                                                     String specialRequirements, String emergencyContact);
    
    List<EventParticipationDTO> getParticipantsByEvent(Long eventId);
    
    <T extends Participable> List<EventParticipationDTO> getParticipationsByParticipant(T participant);
    
    EventParticipationDTO approveParticipation(Long participationId, String responseMessage);
    
    EventParticipationDTO rejectParticipation(Long participationId, String responseMessage);
    
    List<EventParticipationDTO> getPendingParticipantsByOrganizer(Long organizerId);
    
    void cancelParticipation(Long participationId);
}