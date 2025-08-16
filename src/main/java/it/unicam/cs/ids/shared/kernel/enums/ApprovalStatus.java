package it.unicam.cs.ids.shared.kernel.enums;


import lombok.Getter;

/**
 * The approval status of an entity.
 * The statuses include:
 * <li> <b>DRAFT:</b> The entity is in draft state and not yet submitted for approval.</li>
 * <li> <b>PENDING: </b> The entity is awaiting approval.</li>
 * <li> <b>APPROVED:</b> The entity has been approved.</li>
 * <li> <b>REJECTED:</b> The entity has been rejected.</li>
 */
@Getter
public enum ApprovalStatus {
    /** The entity is in draft state and not yet submitted for approval. */
    DRAFT,
    /** The entity is awaiting approval. */
    PENDING,
    /** The entity has been approved. */
    APPROVED,
    /** The entity has been rejected. */
    REJECTED;

    /** The string representation of the approval status. */
    private final String status;

    /**
     * Returns the string representation of the approval status.
     */
    ApprovalStatus() {
        this.status = this.name();
    }
}
