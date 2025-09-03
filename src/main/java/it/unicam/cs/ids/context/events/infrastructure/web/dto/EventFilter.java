package it.unicam.cs.ids.context.events.infrastructure.web.dto;

import it.unicam.cs.ids.context.catalog.domain.model.ApprovalStatus;
import it.unicam.cs.ids.shared.application.FilterParam;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Data 
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor 
@AllArgsConstructor
@SuperBuilder
public class EventFilter extends FilterParam {
    private Long organizerId;
    private LocalDateTime startDateFrom;
    private LocalDateTime startDateTo;
    private LocalDateTime endDateFrom;
    private LocalDateTime endDateTo;
    private Boolean isPublic;
    private Boolean registrationOpen;
    private List<String> tags;
}
