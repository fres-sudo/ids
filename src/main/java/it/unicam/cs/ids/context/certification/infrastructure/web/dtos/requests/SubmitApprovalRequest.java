package it.unicam.cs.ids.context.certification.infrastructure.web.dtos.requests;

import it.unicam.cs.ids.context.certification.domain.model.RequestEntityType;
import it.unicam.cs.ids.shared.application.DTO;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
public class SubmitApprovalRequest extends DTO implements Serializable {
    private final Long companyId;
    private final RequestEntityType entityType;
    private final Long entityId;
}
