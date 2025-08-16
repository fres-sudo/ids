package it.unicam.cs.ids.web.requests.company;

import it.unicam.cs.ids.shared.kernel.enums.RequestEntityType;
import it.unicam.cs.ids.dto.DTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * SubmitApprovalRequest is used to submit an approval request for a specific entity.
 */
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
public class SubmitApprovalRequest extends DTO implements Serializable {
    private final Long companyId;
    private final RequestEntityType entityType;
    private final Long entityId;
}
