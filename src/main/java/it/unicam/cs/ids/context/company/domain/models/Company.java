package it.unicam.cs.ids.context.company.domain.models;

import it.unicam.cs.ids.shared.infrastructure.persistence.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Company represents a signed-up seller on the platform.
 * Companies can sell single products or bundles of products, depending on their type.
 * <ul>
 *     <li><b>Each company must have at least:</b></li>
 *     <ul>
 *         <li><code>name</code></li>
 *         <li><code>email</code> (& <code>password</code>)</li>
 *         <li><code>VAT number</code></li>
 *     </ul>
 *     Where both the email and VAT number are unique identifiers for the company.
 * </ul>
 */
@Entity
@Table(name = "companies", schema = "ids_schema")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Company extends BaseEntity {
    /** Company email (unique username) */
    @Column(nullable = false, unique = true)
    private String email;

    /** Company VAT number */
    @Column(nullable = false, unique = true, length = 20)
    private String vat;

    /** Company account hashed password */
    @Column(name = "hashed_password", nullable = false)
    private String hashedPassword;

    /////////////////////////////////////////////
    /** Company description */
    @Column(columnDefinition = "TEXT")
    private String description;
    /** Company phone number */
    @Column(name = "phone_number", length = 20)
    private String phoneNumber;
    /** Company's email verified status */
    @Column(name = "email_verified", nullable = false)
    private boolean emailVerified = true; // Initialize to true for testing purposes
    /** Role of the company in the platform */
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private CompanyRoles role = CompanyRoles.PRODUCER;
    /** Date when the company was verified */
    @Column(name = "verified_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date verifiedAt;
    /** Thr Company's address */
    @Column(length = 500)
    private String address;
    /** The Company's website */
    @Column()
    private String website;
    /** The Company's billing information */
    @Column(name = "billing_information", columnDefinition = "TEXT")
    private String billingInformation;
}