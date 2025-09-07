package it.unicam.cs.ids.context.events.infrastructure.web.dto.requests;

import it.unicam.cs.ids.shared.application.DTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data @EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class CreateParticipationRequest extends DTO {
    private Long eventId;
    private Long participantId;
    private String applicationMessage;
    private String specialRequirements;
    private String emergencyContact;
}