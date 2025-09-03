package it.unicam.cs.ids.context.events.application.mappers;

import it.unicam.cs.ids.context.events.domain.model.EventParticipation;
import it.unicam.cs.ids.context.events.domain.repositories.EventRepository;
import it.unicam.cs.ids.context.events.infrastructure.web.dto.EventParticipationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EventParticipationMapper {


    public EventParticipationDTO toDto(EventParticipation participation) {
        if (participation == null) {
            return null;
        }

        EventParticipationDTO dto = new EventParticipationDTO();
        dto.setId(participation.getId());
        dto.setParticipantId(participation.getParticipantId());
        dto.setParticipantType(participation.getParticipantType());
        dto.setParticipantIdentifier(participation.getParticipantIdentifier());
        dto.setParticipantContactInfo(participation.getParticipantContactInfo());
        dto.setStatus(participation.getStatus());
        dto.setApplicationMessage(participation.getApplicationMessage());
        dto.setResponseMessage(participation.getResponseMessage());
        dto.setAppliedAt(participation.getAppliedAt());
        dto.setRespondedAt(participation.getRespondedAt());
        dto.setSpecialRequirements(participation.getSpecialRequirements());
        dto.setEmergencyContact(participation.getEmergencyContact());
        
        return dto;
    }
}