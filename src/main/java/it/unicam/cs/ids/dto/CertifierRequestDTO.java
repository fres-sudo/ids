package it.unicam.cs.ids.dto;

import it.unicam.cs.ids.shared.kernel.enums.ApprovalStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Represents a request for certification of a user.
 * This DTO is used to transfer certifier request data between layers.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CertifierRequestDTO extends DTO implements Serializable {
    private Long id;
    private UserDTO requestingUser;
    private ApprovalStatus status;
    private LocalDateTime submittedAt;
    private LocalDateTime processedAt;
    private String comments;
}
