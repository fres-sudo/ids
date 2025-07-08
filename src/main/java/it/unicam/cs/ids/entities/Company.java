package it.unicam.cs.ids.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Company represents a signed-up seller on the platform.
 * Companies can sell single products or bundles of products.
 */
@Entity
@Table(name = "companies", schema = "ids_schema")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Company extends BaseEntity {
    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "email_verified", nullable = false)
    private boolean emailVerified = true; // Initialize to true for testing purposes

    @Column(name = "hashed_password", nullable = false, length = 255)
    private String hashedPassword;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 500)
    private String address;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "verified_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date verifiedAt;

    @Column()
    private String website;

    @Column(nullable = false, unique = true, length = 20)
    private String vat;

    @Column(name = "billing_information", columnDefinition = "TEXT")
    private String billingInformation;

    @Column()
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> employees = new ArrayList<>(); // List of employees associated with the company
}