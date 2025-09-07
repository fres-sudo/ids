package it.unicam.cs.ids.shared.application;

import it.unicam.cs.ids.context.catalog.domain.model.ApprovalStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface Approvable {
    void setApprovalStatus(ApprovalStatus approvalStatus);
    ApprovalStatus getStatus();
    LocalDateTime getUpdatedAt();
    Long getId();
}
