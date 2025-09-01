package it.unicam.cs.ids.context.events.infrastructure.web.dto;

import it.unicam.cs.ids.context.catalog.domain.model.ApprovalStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventParticipationDTO {
    private Long id;
    private EventDTO event;
    private Long participantId;
    private String participantType;
    private String participantIdentifier;
    private String participantContactInfo;
    private ApprovalStatus status;
    private String applicationMessage;
    private String responseMessage;
    private LocalDateTime appliedAt;
    private LocalDateTime respondedAt;
    private String specialRequirements;
    private String emergencyContact;
}