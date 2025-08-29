package it.unicam.cs.ids.context.events.infrastructure.web.dto;

import it.unicam.cs.ids.context.catalog.domain.model.ApprovalStatus;
import it.unicam.cs.ids.context.company.infrastructure.web.dtos.CompanyDTO;
import it.unicam.cs.ids.shared.application.DTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper=false)
public class EventParticipationDTO extends DTO implements Serializable {
    private Long id;
    private String name;
    private Date createdAt;
    private EventDTO event;
    private CompanyDTO participant;
    private ApprovalStatus status;
    private String applicationMessage;
    private String responseMessage;
    private LocalDateTime appliedAt;
    private LocalDateTime respondedAt;
    private String specialRequirements;
    private String emergencyContact;
    private boolean canBeModified;
}
