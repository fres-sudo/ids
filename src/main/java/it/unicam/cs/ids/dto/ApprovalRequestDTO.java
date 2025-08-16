package it.unicam.cs.ids.dto;

import it.unicam.cs.ids.shared.kernel.enums.ApprovalStatus;
import it.unicam.cs.ids.shared.application.Approvable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Represents a request for approval of an entity that implements the {@link Approvable} interface.
 * This DTO is used to transfer approval request data between layers.
 *
 * @param <T> the type of the entity being approved, which must implement {@link Approvable}
 */
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
