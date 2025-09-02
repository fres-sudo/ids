package it.unicam.cs.ids.context.identity.infrastructure.web.dtos;

import it.unicam.cs.ids.context.catalog.domain.model.ApprovalStatus;
import it.unicam.cs.ids.context.identity.domain.model.RequestType;
import it.unicam.cs.ids.shared.application.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * DTO for role requests that handles both Certifier and Animator requests
 * following the unified design pattern.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class RoleRequestDTO extends DTO {
    private Long id;
    private Long requestingUserId;
    private String requestingUserEmail;
    private String requestingUserName;
    private RequestType requestType;
    private ApprovalStatus status;
    private LocalDateTime submittedAt;
    private LocalDateTime processedAt;
    private String comments;
}