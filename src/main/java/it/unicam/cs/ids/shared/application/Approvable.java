package it.unicam.cs.ids.shared.application;

import it.unicam.cs.ids.shared.kernel.enums.ApprovalStatus;

/**
 * Interface representing an entity that can be approved or rejected.
 * It provides a method to set the approval status of the entity.
 */
public interface Approvable {
    /**
     * Sets the approval status of the entity.
     *
     * @param approvalStatus the new approval status to set
     */
    void setApprovalStatus(ApprovalStatus approvalStatus);
}
