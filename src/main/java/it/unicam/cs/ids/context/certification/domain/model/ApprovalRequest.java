package it.unicam.cs.ids.context.certification.domain.model;

import it.unicam.cs.ids.context.catalog.domain.model.ApprovalStatus;
import it.unicam.cs.ids.context.catalog.domain.model.Product;
import it.unicam.cs.ids.context.company.domain.models.Company;
import it.unicam.cs.ids.shared.infrastructure.persistence.BaseEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Certificate represents a document issued for a product, which is a proof of authenticity or compliance to territorial regulations.
 * All certificates are issued by a registered Certifier (a special user), that is responsible for the validity of the certificate.
 * <b>Every purchasable product</b> (thus platform-visible for signed & unsigned users)
 * <b> should have a certificate</b>, if not it is considered a pending item, waiting for the certifier to issue it.
 *
 */
@Entity
@Table(name = "approval_requests", schema = "ids_schema")
@Data
public class ApprovalRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Company requestingCompany;

    @Enumerated(EnumType.STRING)
    private RequestEntityType entityType; // "PRODUCT", "BUNDLE", "EVENT"

    private Long entityId;

    @Enumerated(EnumType.STRING)
    private ApprovalStatus status = ApprovalStatus.PENDING;

    @CreationTimestamp
    private LocalDateTime submittedAt;
    @UpdateTimestamp
    private LocalDateTime processedAt;

    @Nullable
    private String comments;
}