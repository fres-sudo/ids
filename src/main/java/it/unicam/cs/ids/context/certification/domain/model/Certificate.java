package it.unicam.cs.ids.context.certification.domain.model;

import it.unicam.cs.ids.context.catalog.domain.model.Product;
import it.unicam.cs.ids.context.company.domain.models.Company;
import it.unicam.cs.ids.shared.infrastructure.persistence.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * Certificate represents a document issued for a product, which is a proof of authenticity or compliance to territorial regulations.
 * All certificates are issued by a registered Certifier (a special user), that is responsible for the validity of the certificate.
 * <b>Every purchasable product</b> (thus platform-visible for signed & unsigned users)
 * <b> should have a certificate</b>, if not it is considered a pending item, waiting for the certifier to issue it.
 *
 */
@Entity
@Table(name = "certificates", schema = "ids_schema")
@Data
@EqualsAndHashCode(callSuper = true)
public class Certificate extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "issuer_company_id", nullable = false)
    private Company issuer;

    @Column(name = "certification_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date certificationDate;

    @Column(name = "expiration_date")
    @Temporal(TemporalType.DATE)
    private Date expirationDate;

    @Column(name = "issue_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date issueDate;
}