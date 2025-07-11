package it.unicam.cs.ids.entities;

import it.unicam.cs.ids.enums.PlatformRoles;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * User represents a signed-up user on the platform.
 */
@Entity
@Table(name = "users", schema = "ids_schema")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {
    @Column(nullable = false, length = 255)
    private String surname;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "hashed_password", nullable = false, length = 255)
    private String hashedPassword;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(length = 500)
    private String address;

    @Column(name = "email_verified", nullable = false)
    private boolean emailVerified = true; // Initialize to true for testing purposes

    @Column(name = "verified_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date verifiedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private PlatformRoles role = PlatformRoles.BUYER;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
}