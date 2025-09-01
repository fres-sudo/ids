package it.unicam.cs.ids.context.events.infrastructure.web.dto.requests;

import lombok.Data;

@Data
public class UpdateParticipationRequest {
    private String applicationMessage;
    private String specialRequirements;
    private String emergencyContact;
}