package it.unicam.cs.ids.context.events.infrastructure.web.dto;

import it.unicam.cs.ids.context.catalog.domain.model.ApprovalStatus;
import it.unicam.cs.ids.context.company.infrastructure.web.dtos.CompanyDTO;
import it.unicam.cs.ids.context.identity.infrastructure.web.dtos.UserDTO;
import it.unicam.cs.ids.shared.application.DTO;
import it.unicam.cs.ids.shared.infrastructure.persistence.Coordinates;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class EventDTO extends DTO implements Serializable {
    private Long id;
    private String name;
    private String description;
    private Date createdAt;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime registrationDeadline;
    private Integer maxParticipants;
    private Double participationFee;
    private ApprovalStatus status;
    private UserDTO organizer;
    private Coordinates eventLocation;
    private List<String> tags;
    private List<String> imageUrls;
    private String requirements;
    private String contactInfo;
    private Boolean isPublic;
    private List<EventParticipationDTO> participations;
    private Boolean registrationOpen;
    private Boolean hasAvailableSlots;
}
