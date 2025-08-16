package it.unicam.cs.ids.dto;

import it.unicam.cs.ids.shared.kernel.enums.ApprovalStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

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
