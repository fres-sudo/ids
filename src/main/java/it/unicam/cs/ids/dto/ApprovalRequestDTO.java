package it.unicam.cs.ids.dto;

import it.unicam.cs.ids.shared.kernel.enums.ApprovalStatus;
import it.unicam.cs.ids.shared.application.Approvable;
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
