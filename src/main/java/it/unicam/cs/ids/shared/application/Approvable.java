package it.unicam.cs.ids.shared.application;

import it.unicam.cs.ids.shared.kernel.enums.ApprovalStatus;

public interface Approvable {
    void setApprovalStatus(ApprovalStatus approvalStatus);
}
