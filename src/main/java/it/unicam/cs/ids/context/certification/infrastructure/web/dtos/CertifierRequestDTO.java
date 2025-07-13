package it.unicam.cs.ids.context.certification.infrastructure.web.dtos;

import it.unicam.cs.ids.context.catalog.domain.model.ApprovalStatus;
import it.unicam.cs.ids.context.identity.infrastructure.web.dtos.UserDTO;
import it.unicam.cs.ids.shared.application.DTO;
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
