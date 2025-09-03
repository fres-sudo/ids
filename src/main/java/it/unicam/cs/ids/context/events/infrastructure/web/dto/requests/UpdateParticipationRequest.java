package it.unicam.cs.ids.context.events.infrastructure.web.dto.requests;

import it.unicam.cs.ids.shared.application.DTO;
import lombok.Data;

@Data
public class UpdateParticipationRequest extends DTO {
    private String applicationMessage;
    private String specialRequirements;
    private String emergencyContact;
}