package it.unicam.cs.ids.context.certification.infrastructure.web.dtos;

import it.unicam.cs.ids.context.catalog.domain.model.ApprovalStatus;
import it.unicam.cs.ids.context.certification.domain.model.Approvable;
import it.unicam.cs.ids.context.company.infrastructure.web.dtos.CompanyDTO;
import it.unicam.cs.ids.shared.application.DTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class ApprovalRequestDTO<T extends Approvable> extends DTO implements Serializable {
    private Long id;
    private CompanyDTO requestingCompany;
    private T entity;
    private Long entityId;
    private ApprovalStatus status;
    private LocalDateTime submittedAt;
    private LocalDateTime processedAt;
    private String comments;
}
