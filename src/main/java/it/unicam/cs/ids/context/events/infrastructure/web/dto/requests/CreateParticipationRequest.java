package it.unicam.cs.ids.context.events.infrastructure.web.dto.requests;

import it.unicam.cs.ids.shared.application.DTO;
import lombok.Data;

@Data
public class CreateParticipationRequest extends DTO {
    private Long eventId;
    private Long participantId;
    private String applicationMessage;
    private String specialRequirements;
    private String emergencyContact;
}