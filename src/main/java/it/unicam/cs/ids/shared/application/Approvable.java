package it.unicam.cs.ids.shared.application;

import it.unicam.cs.ids.context.catalog.domain.model.ApprovalStatus;

public interface Approvable {
    void setApprovalStatus(ApprovalStatus approvalStatus);
    ApprovalStatus getApprovalStatus();
}
