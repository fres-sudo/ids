package it.unicam.cs.ids.context.certification.domain.model;

import it.unicam.cs.ids.context.company.domain.models.Company;
import it.unicam.cs.ids.shared.infrastructure.persistence.ApprovableEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Certificate represents a document issued for a product, which is a proof of authenticity or compliance to territorial regulations.
 * All certificates are issued by a registered Certifier (a special user), that is responsible for the validity of the certificate.
 * <b>Every purchasable product</b> (thus platform-visible for signed & unsigned users)
 * <b> should have a certificate</b>, if not it is considered a pending item, waiting for the certifier to issue it.
 *
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "approval_requests", schema = "ids_schema")
@Data
public class ApprovalRequest extends ApprovableEntity {
    @ManyToOne
    private Company requestingCompany;

    @Enumerated(EnumType.STRING) // "PRODUCT", "BUNDLE")
    private RequestEntityType entityType;

    private Long entityId;
}